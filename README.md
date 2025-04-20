# illsang-backend

### 모듈 구조와 도메인 책임 정의

| 모듈 | 책임 도메인 | 설명 |
|------|--------------|------|
| `module-user` | 사용자 및 상태 정보 | 유저와 경험치 등 상태 변화 중심 |
| `module-quest` | 퀘스트 수행 및 이력 | 도전과제, 미션, 퀴즈 포함 복합 도메인 |
| `module-management` | 운영자 리소스 관리 | 전시용 정보(배너) 중심 |
| `module-query` | 조회 조합 API | 다중 도메인 Read 전용 처리 |
| `module-common` | 범용 유틸 | 전역 기능, 공통 설정 |

> 세부사항은 [설계문서](/docs/multimodule.md) 참조

### module-bootstrap 모듈 
`module-bootstrap` 모듈은 전체 멀티모듈 시스템의 **진입점(Entry Point)** 역할을 수행합니다.  
단일 JAR로 빌드되어 실행되며, 내부에 모든 도메인 모듈(`user`, `quest`, `management`, `query`)이 포함됩니다.

> 세부사항은 [설계문서](/docs/module-bootstrap.md) 참조
