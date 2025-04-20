# module-bootstrap 소개 및 실행 방식

## 개요
`module-bootstrap` 모듈은 전체 멀티모듈 시스템의 **진입점(Entry Point)** 역할을 수행합니다.  
단일 JAR로 빌드되어 실행되며, 내부에 모든 도메인 모듈(`user`, `quest`, `management`, `query`)이 포함됩니다.

> "모든 기능을 하나의 서비스로 실행하되, 모듈별 책임과 경계를 유지하는 구조"를 추구합니다.

---

## ✅ 역할
| 목적 | 설명 |
|------|------|
| **앱 실행 진입점** | Spring Boot의 `@SpringBootApplication`을 보유 |
| **모든 도메인 모듈 통합** | 각 모듈을 의존성으로 포함하여 통합 실행 |
| **추후 분리 용이성 확보** | 향후 MSA로의 이관 시 각 모듈을 독립 서비스로 분리하기 쉬움 |
| **Docker 배포 편의** | 하나의 JAR만으로 도커라이징 가능 |

---

## 🏗 빌드 방법
Gradle로 멀티모듈을 구성한 이후, 다음 명령어로 실행 JAR을 생성합니다:

```bash
./gradlew :module-bootstrap:bootJar
```

생성된 파일:
```
build/libs/module-bootstrap-0.0.1.jar
```

---

## 🚀 실행 방법

```bash
java -jar module-bootstrap/build/libs/module-bootstrap-0.0.1.jar
```

> application.yml은 이 모듈 내에 `application.yml` 또는 `application-<profile>.yml`로 구성되어 있어야 합니다.
