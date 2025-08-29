#!/bin/bash
set -e

# --- 환경 변수 및 설정 ---
APP_DIR="/home/ssm-user/app"
ENV_FILE="$APP_DIR/deploy.env"
NGINX_CONFIG="/etc/nginx/conf.d/service-url.inc"
BLUE_PORT=8081
GREEN_PORT=8082

cleanup() {
  echo "> 스크립트가 종료됩니다. 사용하지 않는 모든 도커 이미지를 정리합니다..."
  # -f: 확인 질문 없이 바로 삭제
  sudo docker image prune -a -f
}

trap cleanup EXIT

# GitHub Actions에서 생성한 환경 변수 파일 로드
if [ -f "$ENV_FILE" ]; then
  echo "> Loading environment variables from $ENV_FILE"
  source "$ENV_FILE"
else
  echo "> [Error] deploy.env file not found."
  exit 1
fi

IMAGE_NAME="${ECR_REGISTRY}/${ECR_REPOSITORY}:${IMAGE_TAG}"

# --- 1. 현재 활성 포트(Blue) 확인 및 비활성 포트(Green) 결정 ---
if [ ! -f $NGINX_CONFIG ] || ! grep -q "127.0.0.1" $NGINX_CONFIG; then
    echo "> Nginx 설정이 없거나 비정상적이므로, Blue(8081)를 기본 활성 포트로 간주합니다."
    CURRENT_PORT=$BLUE_PORT
else
    CURRENT_PORT=$(grep -Po 'http://127.0.0.1:\K[0-9]+' $NGINX_CONFIG)
fi

if [ "$CURRENT_PORT" -eq "$BLUE_PORT" ]; then
    TARGET_PORT=$GREEN_PORT
    TARGET_CONTAINER_NAME="${DOCKER_CONTAINER_NAME}-green"
else
    TARGET_PORT=$BLUE_PORT
    TARGET_CONTAINER_NAME="${DOCKER_CONTAINER_NAME}-blue"
fi

echo "> 새 버전을 배포할 포트(Green): $TARGET_PORT"
echo "> 새 컨테이너 이름: ${TARGET_CONTAINER_NAME}"

# --- 2. 새 버전(Green) 컨테이너 실행 ---
echo "> ECR에 로그인합니다."
aws ecr get-login-password --region ap-northeast-2 | sudo docker login --username AWS --password-stdin "$ECR_REGISTRY"

echo "> 새 도커 이미지를 pull합니다: $IMAGE_NAME"
sudo docker pull "$IMAGE_NAME"

if [ "$(sudo docker ps -aq -f name="^/${TARGET_CONTAINER_NAME}$")" ]; then
    sudo docker rm -f "$TARGET_CONTAINER_NAME"
fi

echo "> 새 버전의 컨테이너를 실행합니다. (Port: ${TARGET_PORT})"
sudo docker run -d \
  --name "$TARGET_CONTAINER_NAME" \
  -p "${TARGET_PORT}:8080" \
  -e "SPRING_PROFILES_ACTIVE=${SPRING_PROFILES_ACTIVE}" \
  -e "JAVA_OPTS=-XX:MaxRAMPercentage=75.0 -XX:+UseG1GC -XX:MaxGCPauseMillis=100 -XX:MaxMetaspaceSize=200m" \
  --memory="${DOCKER_MEMORY_LIMIT}" \
  --memory-swap="${DOCKER_MEMORY_SWAP_LIMIT}" \
  "$IMAGE_NAME"
