# API Endpoints Documentation

## 개요

이 문서는 각 모듈별로 제공되는 REST API 엔드포인트를 설명합니다.

## 1. Module User API

### 1.1 User Management

#### 사용자 생성
```
POST /api/users
Content-Type: application/json

{
  "name": "string",
  "email": "string",
  "status": "ACTIVE"
}
```

#### 사용자 조회 (ID)
```
GET /api/users/{id}
```

#### 사용자 목록 조회
```
GET /api/users
```

#### 사용자 수정
```
PUT /api/users/{id}
Content-Type: application/json

{
  "name": "string",
  "email": "string",
  "status": "ACTIVE"
}
```

#### 사용자 삭제
```
DELETE /api/users/{id}
```

### 1.2 User XP Management

#### 사용자 XP 생성
```
POST /api/user-xps
Content-Type: application/json

{
  "userId": 1,
  "xpType": "QUEST",
  "point": 100
}
```

#### 사용자 XP 조회 (ID)
```
GET /api/user-xps/{id}
```

#### 사용자별 XP 목록 조회
```
GET /api/user-xps/user/{userId}
```

#### 사용자 XP 수정
```
PUT /api/user-xps/{id}
Content-Type: application/json

{
  "xpType": "QUEST",
  "point": 150
}
```

#### 사용자 XP 삭제
```
DELETE /api/user-xps/{id}
```

#### 포인트 추가
```
POST /api/user-xps/{id}/add-point
Content-Type: application/json

{
  "point": 50
}
```

### 1.3 User XP History Management

#### 사용자 XP 히스토리 생성
```
POST /api/user-xp-histories
Content-Type: application/json

{
  "userId": 1,
  "xpType": "QUEST",
  "point": 100,
  "targetType": "QUEST",
  "targetId": 1
}
```

#### 사용자 XP 히스토리 조회 (ID)
```
GET /api/user-xp-histories/{id}
```

#### 사용자별 XP 히스토리 목록 조회
```
GET /api/user-xp-histories/user/{userId}
```

#### 사용자 XP 히스토리 수정
```
PUT /api/user-xp-histories/{id}
Content-Type: application/json

{
  "xpType": "QUEST",
  "point": 150,
  "targetType": "QUEST",
  "targetId": 1
}
```

#### 사용자 XP 히스토리 삭제
```
DELETE /api/user-xp-histories/{id}
```

### 1.4 User Emoji Management

#### 사용자 이모지 생성
```
POST /api/user-emojis
Content-Type: application/json

{
  "userId": 1,
  "emojiId": 1
}
```

#### 사용자 이모지 조회 (ID)
```
GET /api/user-emojis/{id}
```

#### 사용자별 이모지 목록 조회
```
GET /api/user-emojis/user/{userId}
```

#### 사용자 이모지 수정
```
PUT /api/user-emojis/{id}
Content-Type: application/json

{
  "emojiId": 2
}
```

#### 사용자 이모지 삭제
```
DELETE /api/user-emojis/{id}
```

## 2. Module Management API

### 2.1 Banner Management

#### 배너 생성
```
POST /api/banners
Content-Type: application/json

{
  "title": "string",
  "description": "string",
  "imageUrl": "string",
  "linkUrl": "string",
  "startDate": "2024-01-01T00:00:00",
  "endDate": "2024-12-31T23:59:59",
  "isActive": true
}
```

#### 배너 조회 (ID)
```
GET /api/banners/{id}
```

#### 배너 목록 조회
```
GET /api/banners
```

#### 배너 수정
```
PUT /api/banners/{id}
Content-Type: application/json

{
  "title": "string",
  "description": "string",
  "imageUrl": "string",
  "linkUrl": "string",
  "startDate": "2024-01-01T00:00:00",
  "endDate": "2024-12-31T23:59:59",
  "isActive": true
}
```

#### 배너 삭제
```
DELETE /api/banners/{id}
```

### 2.2 Image Management

#### 이미지 생성
```
POST /api/images
Content-Type: application/json

{
  "name": "string",
  "url": "string",
  "altText": "string",
  "width": 1920,
  "height": 1080
}
```

#### 이미지 조회 (ID)
```
GET /api/images/{id}
```

#### 이미지 목록 조회
```
GET /api/images
```

#### 이미지 수정
```
PUT /api/images/{id}
Content-Type: application/json

{
  "name": "string",
  "url": "string",
  "altText": "string",
  "width": 1920,
  "height": 1080
}
```

#### 이미지 삭제
```
DELETE /api/images/{id}
```

## 3. Module Quest API

### 3.1 Quest Management

#### 퀘스트 생성
```
POST /api/quests
Content-Type: application/json

{
  "title": "string",
  "description": "string",
  "xpReward": 100,
  "status": "ACTIVE"
}
```

#### 퀘스트 조회 (ID)
```
GET /api/quests/{id}
```

#### 퀘스트 목록 조회
```
GET /api/quests
```

#### 퀘스트 수정
```
PUT /api/quests/{id}
Content-Type: application/json

{
  "title": "string",
  "description": "string",
  "xpReward": 150,
  "status": "ACTIVE"
}
```

#### 퀘스트 삭제
```
DELETE /api/quests/{id}
```

### 3.2 Mission Management

#### 미션 생성
```
POST /api/missions
Content-Type: application/json

{
  "questId": 1,
  "title": "string",
  "description": "string",
  "order": 1,
  "status": "ACTIVE"
}
```

#### 미션 조회 (ID)
```
GET /api/missions/{id}
```

#### 미션 목록 조회
```
GET /api/missions
```

#### 퀘스트별 미션 목록 조회
```
GET /api/missions/quest/{questId}
```

#### 미션 수정
```
PUT /api/missions/{id}
Content-Type: application/json

{
  "title": "string",
  "description": "string",
  "order": 2,
  "status": "ACTIVE"
}
```

#### 미션 삭제
```
DELETE /api/missions/{id}
```

### 3.3 Quiz Management

#### 퀴즈 생성
```
POST /api/quizzes
Content-Type: application/json

{
  "missionId": 1,
  "question": "string",
  "type": "MULTIPLE_CHOICE"
}
```

#### 퀴즈 조회 (ID)
```
GET /api/quizzes/{id}
```

#### 퀴즈 목록 조회
```
GET /api/quizzes
```

#### 미션별 퀴즈 목록 조회
```
GET /api/quizzes/mission/{missionId}
```

#### 퀴즈 수정
```
PUT /api/quizzes/{id}
Content-Type: application/json

{
  "question": "string",
  "type": "MULTIPLE_CHOICE"
}
```

#### 퀴즈 삭제
```
DELETE /api/quizzes/{id}
```

### 3.4 Quiz Answer Management

#### 퀴즈 답변 생성
```
POST /api/quiz-answers
Content-Type: application/json

{
  "quizId": 1,
  "answer": "string",
  "isCorrect": true,
  "order": 1
}
```

#### 퀴즈 답변 조회 (ID)
```
GET /api/quiz-answers/{id}
```

#### 퀴즈 답변 목록 조회
```
GET /api/quiz-answers
```

#### 퀴즈별 답변 목록 조회
```
GET /api/quiz-answers/quiz/{quizId}
```

#### 퀴즈 답변 수정
```
PUT /api/quiz-answers/{id}
Content-Type: application/json

{
  "answer": "string",
  "isCorrect": false,
  "order": 2
}
```

#### 퀴즈 답변 삭제
```
DELETE /api/quiz-answers/{id}
```

### 3.5 Quest Reward Management

#### 퀘스트 보상 생성
```
POST /api/quest-rewards
Content-Type: application/json

{
  "questId": 1,
  "rewardType": "XP",
  "amount": 100,
  "description": "string"
}
```

#### 퀘스트 보상 조회 (ID)
```
GET /api/quest-rewards/{id}
```

#### 퀘스트 보상 목록 조회
```
GET /api/quest-rewards
```

#### 퀘스트별 보상 목록 조회
```
GET /api/quest-rewards/quest/{questId}
```

#### 퀘스트 보상 수정
```
PUT /api/quest-rewards/{id}
Content-Type: application/json

{
  "rewardType": "XP",
  "amount": 150,
  "description": "string"
}
```

#### 퀘스트 보상 삭제
```
DELETE /api/quest-rewards/{id}
```

## 4. 공통 응답 형식

### 4.1 성공 응답
```json
{
  "id": 1,
  "createdBy": "system",
  "createdAt": "2024-01-01T00:00:00",
  "updatedBy": "system",
  "updatedAt": "2024-01-01T00:00:00"
}
```

### 4.2 에러 응답
```json
{
  "timestamp": "2024-01-01T00:00:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed",
  "path": "/api/users"
}
```

## 5. HTTP 상태 코드

- `200 OK`: 요청 성공
- `201 Created`: 리소스 생성 성공
- `400 Bad Request`: 잘못된 요청
- `404 Not Found`: 리소스를 찾을 수 없음
- `500 Internal Server Error`: 서버 내부 오류

## 6. 인증 및 권한

현재 모든 API는 인증 없이 접근 가능합니다. 향후 JWT 토큰 기반 인증이 추가될 예정입니다.

## 7. 페이지네이션

목록 조회 API는 향후 페이지네이션을 지원할 예정입니다:

```
GET /api/users?page=0&size=20&sort=id,desc
```

## 8. 필터링

목록 조회 API는 향후 필터링을 지원할 예정입니다:

```
GET /api/users?status=ACTIVE&name=John
```

## 9. API 버전 관리

현재 API는 v1을 사용합니다. 향후 버전 관리를 위해 URL에 버전을 포함할 예정입니다:

```
/api/v1/users
/api/v2/users
``` 