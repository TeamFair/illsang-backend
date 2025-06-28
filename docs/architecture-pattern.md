# Architecture Pattern Documentation

## 개요

이 프로젝트는 **Domain-Driven Design (DDD)**, **Hexagonal Architecture (Ports & Adapters)**, **Clean Architecture** 패턴을 기반으로 구성되어 있습니다. 각 패턴의 핵심 원칙과 구현 방식을 설명합니다.

## 1. Domain-Driven Design (DDD)

### 1.1 핵심 개념

DDD는 복잡한 비즈니스 도메인을 모델링하고 구현하는 방법론입니다.

#### 주요 구성 요소:
- **Entity**: 고유 식별자를 가진 도메인 객체
- **Value Object**: 불변 객체로 값의 집합
- **Aggregate**: 일관성 경계를 가진 엔티티들의 집합
- **Repository**: 도메인 객체의 영속성을 관리
- **Service**: 도메인 로직을 캡슐화

### 1.2 프로젝트에서의 구현

```
module-user/
├── domain/
│   ├── model/           # 도메인 모델 (UserModel, UserXpModel 등)
│   ├── mapper/          # 도메인 매퍼 (Entity ↔ Model 변환)
│   └── enums/           # 도메인 열거형 (UserStatus, XpType 등)
```

## 2. Hexagonal Architecture (Ports & Adapters)

### 2.1 핵심 개념

Hexagonal Architecture는 비즈니스 로직을 외부 의존성으로부터 격리시키는 아키텍처 패턴입니다.

#### 주요 구성 요소:
- **Port**: 인터페이스 (Inbound/Outbound)
- **Adapter**: 포트의 구현체
- **Application Core**: 비즈니스 로직

### 2.2 프로젝트에서의 구현

```
module-user/
├── application/
│   ├── port/
│   │   └── out/         # Outbound Ports (PersistencePort 등)
│   ├── service/         # Application Services
│   └── command/         # Command Objects
├── adapter/
│   ├── in/              # Inbound Adapters (Web Controllers)
│   └── out/             # Outbound Adapters (Persistence, External APIs)
```

#### Port 예시:
```kotlin
interface UserXpPersistencePort {
    fun save(userXp: UserXpModel): UserXpModel
    fun findById(id: Long): UserXpModel?
    fun findByUserId(userId: Long): List<UserXpModel>
}
```

#### Adapter 예시:
```kotlin
@Component
class UserXpPersistenceAdapter(
    private val userXpRepository: UserXpRepository
) : UserXpPersistencePort {
    // Port 구현
}
```

## 3. Clean Architecture

### 3.1 핵심 개념

Clean Architecture는 의존성 규칙을 통해 관심사를 분리하는 아키텍처 패턴입니다.

#### 의존성 규칙:
- 내부 계층은 외부 계층에 의존하지 않음
- 의존성은 안쪽을 향함
- 외부 계층은 내부 계층의 인터페이스에 의존

### 3.2 계층 구조

```
┌─────────────────────────────────────┐
│           Presentation Layer        │ ← Controllers, DTOs
├─────────────────────────────────────┤
│           Application Layer         │ ← Services, Commands
├─────────────────────────────────────┤
│           Domain Layer              │ ← Models, Ports
├─────────────────────────────────────┤
│           Infrastructure Layer      │ ← Repositories, Adapters
└─────────────────────────────────────┘
```

## 4. 패턴 적용 예시

### 4.1 UserXp Entity 패턴 적용

#### 1. Entity (Infrastructure Layer)
```kotlin
@Entity
@Table(name = "user_xp")
class UserXpEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val userId: Long,
    val xpType: XpType,
    var point: Int
) : BaseEntity()
```

#### 2. Model (Domain Layer)
```kotlin
data class UserXpModel(
    val id: Long? = null,
    val userId: Long,
    val xpType: XpType,
    val point: Int
) : BaseModel()
```

#### 3. Mapper (Domain Layer)
```kotlin
object UserXpMapper {
    fun toModel(entity: UserXpEntity): UserXpModel
    fun toEntity(model: UserXpModel, userEntity: UserEntity): UserXpEntity
    fun toModel(command: CreateUserXpCommand): UserXpModel
}
```

#### 4. Command (Application Layer)
```kotlin
data class CreateUserXpCommand(
    val userId: Long,
    val xpType: XpType,
    val point: Int
)
```

#### 5. Port (Domain Layer)
```kotlin
interface UserXpPersistencePort {
    fun save(userXp: UserXpModel): UserXpModel
    fun findById(id: Long): UserXpModel?
    fun findByUserId(userId: Long): List<UserXpModel>
}
```

#### 6. Repository (Infrastructure Layer)
```kotlin
interface UserXpRepository : JpaRepository<UserXpEntity, Long> {
    fun findByUserEntityId(userId: Long): List<UserXpEntity>
}
```

#### 7. Persistence Adapter (Infrastructure Layer)
```kotlin
@Component
class UserXpPersistenceAdapter(
    private val userXpRepository: UserXpRepository
) : UserXpPersistencePort {
    // Port 구현
}
```

#### 8. Service (Application Layer)
```kotlin
@Service
@Transactional(readOnly = true)
class UserXpService(
    private val userXpPersistencePort: UserXpPersistencePort
) {
    @Transactional
    fun createUserXp(command: CreateUserXpCommand): UserXpModel
}
```

#### 9. Request/Response DTOs (Presentation Layer)
```kotlin
data class CreateUserXpRequest(
    val userId: Long,
    val xpType: XpType,
    val point: Int
)

data class UserXpResponse(
    val id: Long?,
    val userId: Long,
    val xpType: XpType,
    val point: Int
)
```

#### 10. Controller (Presentation Layer)
```kotlin
@RestController
@RequestMapping("/api/user-xps")
class UserXpController(
    private val userXpService: UserXpService
) {
    @PostMapping
    fun createUserXp(@RequestBody request: CreateUserXpRequest): ResponseEntity<UserXpResponse>
}
```

## 5. 패턴의 장점

### 5.1 테스트 용이성
- 각 계층이 독립적이어서 단위 테스트 작성이 쉬움
- Mock 객체를 통한 의존성 격리

### 5.2 유지보수성
- 관심사 분리로 인한 코드 가독성 향상
- 변경 사항이 다른 계층에 미치는 영향 최소화

### 5.3 확장성
- 새로운 기능 추가 시 기존 코드 수정 최소화
- 새로운 어댑터 추가가 용이

### 5.4 도메인 중심 설계
- 비즈니스 로직이 도메인 모델에 집중
- 기술적 세부사항과 도메인 로직의 분리

## 6. 모듈 구조

### 6.1 module-user
사용자 관련 도메인을 담당하는 모듈

### 6.2 module-management
관리 기능을 담당하는 모듈 (배너, 이미지 등)

### 6.3 module-quest
퀘스트 관련 도메인을 담당하는 모듈

### 6.4 module-common
공통 기능을 담당하는 모듈

## 7. 개발 가이드라인

### 7.1 새로운 Entity 추가 시
1. Entity 클래스 생성
2. Model 클래스 생성
3. Mapper 클래스 생성/수정
4. Command 클래스들 생성
5. Port 인터페이스 생성
6. Repository 인터페이스 생성
7. Persistence Adapter 생성
8. Service 클래스 생성
9. Request/Response DTO 생성
10. Controller 클래스 생성

### 7.2 의존성 주입
- 생성자 주입을 통한 의존성 관리
- 인터페이스를 통한 느슨한 결합

### 7.3 트랜잭션 관리
- Service 계층에서 트랜잭션 관리
- 읽기 전용 메서드에는 `@Transactional(readOnly = true)` 적용

## 8. 결론

이 아키텍처 패턴을 통해 비즈니스 로직의 핵심을 보호하면서도 유연하고 확장 가능한 시스템을 구축할 수 있습니다. 각 계층의 역할이 명확히 분리되어 있어 팀 협업과 코드 유지보수가 용이합니다. 