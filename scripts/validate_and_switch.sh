#!/bin/bash
set -e

# --- 환경 변수 및 설정 ---
APP_DIR="/home/ssm-user/app"
ENV_FILE="$APP_DIR/deploy.env"
NGINX_CONFIG="/etc/nginx/conf.d/service-url.inc"
BLUE_PORT=8081
GREEN_PORT=8082

# 환경 변수 파일 로드
if [ -f "$ENV_FILE" ]; then
  source "$ENV_FILE"
else
  echo "> [Error] deploy.env file not found."
  exit 1
fi

# --- 1. Green 포트 및 컨테이너 이름 결정 ---
if [ ! -f $NGINX_CONFIG ] || ! grep -q "127.0.0.1" $NGINX_CONFIG; then
    CURRENT_PORT=$BLUE_PORT
else
    CURRENT_PORT=$(grep -Po 'http://127.0.0.1:\K[0-9]+' $NGINX_CONFIG)
fi

if [ "$CURRENT_PORT" -eq "$BLUE_PORT" ]; then
    TARGET_PORT=$GREEN_PORT
    NEW_CONTAINER_NAME="${DOCKER_CONTAINER_NAME}-green"
    OLD_CONTAINER_NAME="${DOCKER_CONTAINER_NAME}-blue"
else
    TARGET_PORT=$BLUE_PORT
    NEW_CONTAINER_NAME="${DOCKER_CONTAINER_NAME}-blue"
    OLD_CONTAINER_NAME="${DOCKER_CONTAINER_NAME}-green"
fi

# --- 2. 헬스 체크 ---
echo "> 새 컨테이너 헬스 체크를 시작합니다 (Port: ${TARGET_PORT})..."
HEALTH_CHECK_URL="http://127.0.0.1:${TARGET_PORT}/actuator/health"

for i in {1..15}; do
    if response_code=$(curl -s -o /dev/null -w "%{http_code}" "$HEALTH_CHECK_URL"); then
        if [ "$response_code" -eq 200 ]; then
            echo "> 헬스 체크 성공 (상태 코드: $response_code)."

            # --- 3. Nginx 트래픽 전환 ---
            echo "> Nginx 리버스 프록시를 ${TARGET_PORT} 포트로 전환합니다."
            echo "set \$service_url http://127.0.0.1:${TARGET_PORT};" | sudo tee $NGINX_CONFIG

            echo "> Nginx 설정을 리로드합니다."
            sudo nginx -s reload

            # --- 4. 이전 버전(Blue) 컨테이너 정리 ---
            echo "> 이전 버전 컨테이너(${OLD_CONTAINER_NAME})를 중지하고 삭제합니다."
            if [ "$(sudo docker ps -aq -f name="^/${OLD_CONTAINER_NAME}$")" ]; then
                sudo docker rm -f "$OLD_CONTAINER_NAME"
            fi

            echo "> Blue/Green 배포가 성공적으로 완료되었습니다."
            exit 0
        else
            echo "> 헬스 체크 시도 $i: 애플리케이션 응답은 왔지만, 아직 준비되지 않았습니다 (HTTP 상태 코드: $response_code). 5초 후 재시도..."
        fi
    else
        # curl 명령 자체가 실패한 경우 (연결 불가)
        curl_fail_code=$?
        echo "> 헬스 체크 시도 $i: curl 실행 실패 (종료 코드: $curl_fail_code). 애플리케이션에 연결할 수 없습니다. 5초 후 재시도..."
    fi
    sleep 5
done

echo "> 헬스 체크에 최종 실패했습니다. 배포를 롤백합니다."

echo "> 실패한 컨테이너(${NEW_CONTAINER_NAME})의 로그를 출력합니다."
sudo docker logs "$NEW_CONTAINER_NAME"

echo "> 실패한 컨테이너(${NEW_CONTAINER_NAME})를 삭제합니다."
sudo docker rm -f "$NEW_CONTAINER_NAME"

exit 1
