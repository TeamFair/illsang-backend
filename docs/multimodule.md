## 모듈 구조와 도메인 책임 정의

## 1. module-user
**책임 도메인**: 사용자, 경험치, 이모지 반응

- **주요 테이블**: `user`, `user_xp`, `user_xp_history`, `user_emoji`
- **Aggregate Root**: `User`
- **설명**: 사용자의 상태와 활동(경험치, 감정표현 등)을 관리하며, 시스템 내 모든 유저 관련 변경 로직은 이 모듈에 집중됩니다.

---

## 2. module-quest
**책임 도메인**: 퀘스트, 미션, 퀴즈, 유저 퀘스트 이력

- **주요 테이블**: `quest`, `mission`, `quest_reward`, `quiz`, `quiz_answer`, `user_quest_history`, `user_mission_history`, `user_quiz_history`
- **Aggregate Root**: `Quest`, `QuestProgress`
- **설명**: 퀘스트 생성, 퀴즈 구성, 보상 지급 및 유저의 진행 이력 추적 등 퀘스트 관련 기능 전반을 담당합니다.

---

## 3. module-management
**책임 도메인**: 운영자용 리소스 관리 (배너 등)

- **주요 테이블**: `banner`
- **Aggregate Root**: `Banner`
- **설명**: 홈 화면이나 운영 목적의 UI 요소를 관리합니다. 관리 기능은 추후 확장 가능성을 고려하여 별도 모듈로 분리하였습니다.

---

## 4. module-query
**책임 도메인**: 복합 조회 (조합된 읽기 전용 API)

- **Aggregate 없음 (ReadModel 전용)**
- **설명**: 단일 도메인에서는 처리하기 어려운 조합 조회(API 응답 최적화)를 전담하며, 각 도메인의 `QueryPort`를 활용해 데이터를 조합합니다. 쓰기 로직은 존재하지 않습니다.

---

## 5. module-common
**책임 도메인**: 전역 공통 유틸 및 설정

- **내용**: 예외 처리, 공통 응답 객체, DateTime 설정, 인증 필터 등 도메인에 종속되지 않는 범용 로직을 포함합니다.

---

## 설계 기준 요약

| 모듈 | 책임 도메인 | 설명 |
|------|--------------|------|
| `module-user` | 사용자 및 상태 정보 | 유저와 경험치 등 상태 변화 중심 |
| `module-quest` | 퀘스트 수행 및 이력 | 도전과제, 미션, 퀴즈 포함 복합 도메인 |
| `module-management` | 운영자 리소스 관리 | 전시용 정보(배너) 중심 |
| `module-query` | 조회 조합 API | 다중 도메인 Read 전용 처리 |
| `module-common` | 범용 유틸 | 전역 기능, 공통 설정 |

---

각 모듈은 자신이 소유한 도메인에 대한 **변경 책임을 명확히 가지며**,  
조회 조합은 `module-query`에서 담당해 **도메인 응집도와 API 최적화**를 동시에 달성합니다.
