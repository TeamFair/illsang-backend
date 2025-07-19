# Implementation Guide

## 새로운 Entity 구현 가이드

이 문서는 새로운 Entity를 추가할 때 따라야 할 단계별 가이드를 제공합니다.

## 1. 기본 구조

### 1.1 파일 생성 순서
```
1. Entity (Infrastructure Layer)
2. Model (Domain Layer)
3. Mapper (Domain Layer)
4. Command (Application Layer)
5. Port (Domain Layer)
6. Repository (Infrastructure Layer)
7. Persistence Adapter (Infrastructure Layer)
8. Service (Application Layer)
9. Request/Response DTOs (Presentation Layer)
10. Controller (Presentation Layer)
```

## 2. 단계별 구현

### 2.1 Entity 생성

```kotlin
// module-user/src/main/kotlin/com/illsang/moduleuser/adapter/out/persistence/entity/ExampleEntity.kt
package com.illsang.user.adapter.out.persistence.entity

import com.illsang.common.entity.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "example")
class ExampleEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "name", nullable = false)
    val name: String,

    @Column(name = "description")
    val description: String? = null
) : BaseEntity()
```

### 2.2 Model 생성

```kotlin
// module-user/src/main/kotlin/com/illsang/moduleuser/domain/model/ExampleModel.kt
package com.illsang.user.domain.model

import com.illsang.common.model.BaseModel
import java.time.LocalDateTime

data class ExampleModel(
    val id: Long? = null,
    val name: String,
    val description: String? = null,
    override val createdBy: String? = null,
    override val createdAt: LocalDateTime? = null,
    override val updatedBy: String? = null,
    override val updatedAt: LocalDateTime? = null
) : BaseModel(createdBy, createdAt, updatedBy, updatedAt)
```

### 2.3 Mapper 생성

```kotlin
// module-user/src/main/kotlin/com/illsang/moduleuser/domain/mapper/ExampleMapper.kt
package com.illsang.user.domain.mapper

import com.illsang.user.adapter.out.persistence.entity.ExampleEntity
import com.illsang.user.application.command.CreateExampleCommand
import com.illsang.user.application.command.UpdateExampleCommand
import com.illsang.user.domain.model.ExampleModel

object ExampleMapper {
    fun toModel(entity: ExampleEntity): ExampleModel {
        return ExampleModel(
            id = entity.id,
            name = entity.name,
            description = entity.description,
            createdBy = entity.createdBy,
            createdAt = entity.createdAt,
            updatedBy = entity.updatedBy,
            updatedAt = entity.updatedAt
        )
    }

    fun toEntity(model: ExampleModel): ExampleEntity {
        return ExampleEntity(
            id = model.id,
            name = model.name,
            description = model.description
        ).apply {
            createdBy = model.createdBy
            createdAt = model.createdAt
            updatedBy = model.updatedBy
            updatedAt = model.updatedAt
        }
    }

    fun toModel(command: CreateExampleCommand): ExampleModel {
        return ExampleModel(
            name = command.name,
            description = command.description
        )
    }

    fun toModel(command: UpdateExampleCommand, existing: ExampleModel): ExampleModel {
        return existing.copy(
            name = command.name ?: existing.name,
            description = command.description ?: existing.description
        )
    }
}
```

### 2.4 Command 생성

```kotlin
// module-user/src/main/kotlin/com/illsang/moduleuser/application/command/CreateExampleCommand.kt
package com.illsang.user.application.command

data class CreateExampleCommand(
    val name: String,
    val description: String? = null
)
```

```kotlin
// module-user/src/main/kotlin/com/illsang/moduleuser/application/command/UpdateExampleCommand.kt
package com.illsang.user.application.command

data class UpdateExampleCommand(
    val id: Long,
    val name: String? = null,
    val description: String? = null
)
```

### 2.5 Port 생성

```kotlin
// module-user/src/main/kotlin/com/illsang/moduleuser/application/port/out/ExamplePersistencePort.kt
package com.illsang.user.application.port.out

import com.illsang.user.domain.model.ExampleModel

interface ExamplePersistencePort {
    fun save(example: ExampleModel): ExampleModel
    fun findById(id: Long): ExampleModel?
    fun findAll(): List<ExampleModel>
    fun deleteById(id: Long)
    fun existsById(id: Long): Boolean
}
```

### 2.6 Repository 생성

```kotlin
// module-user/src/main/kotlin/com/illsang/moduleuser/adapter/out/persistence/repository/ExampleRepository.kt
package com.illsang.user.adapter.out.persistence.repository

import com.illsang.user.adapter.out.persistence.entity.ExampleEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ExampleRepository : JpaRepository<ExampleEntity, Long>
```

### 2.7 Persistence Adapter 생성

```kotlin
// module-user/src/main/kotlin/com/illsang/moduleuser/adapter/out/persistence/ExamplePersistenceAdapter.kt
package com.illsang.user.adapter.out.persistence

import com.illsang.user.adapter.out.persistence.repository.ExampleRepository
import com.illsang.user.application.port.out.ExamplePersistencePort
import com.illsang.user.domain.mapper.ExampleMapper
import com.illsang.user.domain.model.ExampleModel
import org.springframework.stereotype.Component

@Component
class ExamplePersistenceAdapter(
    private val exampleRepository: ExampleRepository
) : ExamplePersistencePort {

    override fun save(example: ExampleModel): ExampleModel {
        val entity = ExampleMapper.toEntity(example)
        val savedEntity = exampleRepository.save(entity)
        return ExampleMapper.toModel(savedEntity)
    }

    override fun findById(id: Long): ExampleModel? {
        return exampleRepository.findById(id)
            .map { ExampleMapper.toModel(it) }
            .orElse(null)
    }

    override fun findAll(): List<ExampleModel> {
        return exampleRepository.findAll()
            .map { ExampleMapper.toModel(it) }
    }

    override fun deleteById(id: Long) {
        exampleRepository.deleteById(id)
    }

    override fun existsById(id: Long): Boolean {
        return exampleRepository.existsById(id)
    }
}
```

### 2.8 Service 생성

```kotlin
// module-user/src/main/kotlin/com/illsang/moduleuser/application/service/ExampleService.kt
package com.illsang.user.application.service

import com.illsang.user.application.command.CreateExampleCommand
import com.illsang.user.application.command.UpdateExampleCommand
import com.illsang.user.application.port.out.ExamplePersistencePort
import com.illsang.user.domain.mapper.ExampleMapper
import com.illsang.user.domain.model.ExampleModel
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ExampleService(
    private val examplePersistencePort: ExamplePersistencePort
) {

    @Transactional
    fun createExample(command: CreateExampleCommand): ExampleModel {
        val exampleModel = ExampleMapper.toModel(command)
        return examplePersistencePort.save(exampleModel)
    }

    fun getExampleById(id: Long): ExampleModel? {
        return examplePersistencePort.findById(id)
    }

    fun getAllExamples(): List<ExampleModel> {
        return examplePersistencePort.findAll()
    }

    @Transactional
    fun updateExample(command: UpdateExampleCommand): ExampleModel? {
        val existingExample = examplePersistencePort.findById(command.id) ?: return null
        val updatedExample = ExampleMapper.toModel(command, existingExample)
        return examplePersistencePort.save(updatedExample)
    }

    @Transactional
    fun deleteExample(id: Long): Boolean {
        return if (examplePersistencePort.existsById(id)) {
            examplePersistencePort.deleteById(id)
            true
        } else {
            false
        }
    }
}
```

### 2.9 Request/Response DTOs 생성

```kotlin
// module-user/src/main/kotlin/com/illsang/moduleuser/adapter/`in`/web/model/request/CreateExampleRequest.kt
package com.illsang.user.adapter.`in`.web.model.request

data class CreateExampleRequest(
    val name: String,
    val description: String? = null
)
```

```kotlin
// module-user/src/main/kotlin/com/illsang/moduleuser/adapter/`in`/web/model/request/UpdateExampleRequest.kt
package com.illsang.user.adapter.`in`.web.model.request

data class UpdateExampleRequest(
    val name: String? = null,
    val description: String? = null
)
```

```kotlin
// module-user/src/main/kotlin/com/illsang/moduleuser/adapter/`in`/web/model/response/ExampleResponse.kt
package com.illsang.user.adapter.`in`.web.model.response

import com.illsang.user.domain.model.ExampleModel
import java.time.LocalDateTime

data class ExampleResponse(
    val id: Long?,
    val name: String,
    val description: String?,
    val createdBy: String?,
    val createdAt: LocalDateTime?,
    val updatedBy: String?,
    val updatedAt: LocalDateTime?
) {
    companion object {
        fun from(exampleModel: ExampleModel): ExampleResponse {
            return ExampleResponse(
                id = exampleModel.id,
                name = exampleModel.name,
                description = exampleModel.description,
                createdBy = exampleModel.createdBy,
                createdAt = exampleModel.createdAt,
                updatedBy = exampleModel.updatedBy,
                updatedAt = exampleModel.updatedAt
            )
        }
    }
}
```

### 2.10 Controller 생성

```kotlin
// module-user/src/main/kotlin/com/illsang/moduleuser/adapter/`in`/web/ExampleController.kt
package com.illsang.user.adapter.`in`.web

import com.illsang.common.enums.ResponseMsg
import com.illsang.user.adapter.`in`.web.model.request.CreateExampleRequest
import com.illsang.user.adapter.`in`.web.model.request.UpdateExampleRequest
import com.illsang.user.adapter.`in`.web.model.response.ExampleResponse
import com.illsang.user.application.command.CreateExampleCommand
import com.illsang.user.application.command.UpdateExampleCommand
import com.illsang.user.application.service.ExampleService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/examples")
class ExampleController(
    private val exampleService: ExampleService
) {

    @PostMapping
    fun createExample(@RequestBody request: CreateExampleRequest): ResponseEntity<ExampleResponse> {
        val command = CreateExampleCommand(
            name = request.name,
            description = request.description
        )
        val example = exampleService.createExample(command)
        return ResponseEntity.status(HttpStatus.CREATED).body(ExampleResponse.from(example))
    }

    @GetMapping("/{id}")
    fun getExample(@PathVariable id: Long): ResponseEntity<ExampleResponse> {
        val example = exampleService.getExampleById(id)
        return if (example != null) {
            ResponseEntity.ok(ExampleResponse.from(example))
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @GetMapping
    fun getAllExamples(): ResponseEntity<List<ExampleResponse>> {
        val examples = exampleService.getAllExamples()
        return ResponseEntity.ok(examples.map { ExampleResponse.from(it) })
    }

    @PutMapping("/{id}")
    fun updateExample(
        @PathVariable id: Long,
        @RequestBody request: UpdateExampleRequest
    ): ResponseEntity<ExampleResponse> {
        val command = UpdateExampleCommand(
            id = id,
            name = request.name,
            description = request.description
        )
        val example = exampleService.updateExample(command)
        return if (example != null) {
            ResponseEntity.ok(ExampleResponse.from(example))
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/{id}")
    fun deleteExample(@PathVariable id: Long): ResponseEntity<ResponseMsg> {
        val deleted = exampleService.deleteExample(id)
        return if (deleted) {
            ResponseEntity.ok(ResponseMsg.SUCCESS)
        } else {
            ResponseEntity.notFound().build()
        }
    }
}
```

## 3. 테스트 작성 가이드

### 3.1 Service 테스트 예시

```kotlin
@ExtendWith(MockitoExtension::class)
class ExampleServiceTest {

    @Mock
    private lateinit var examplePersistencePort: ExamplePersistencePort

    @InjectMocks
    private lateinit var exampleService: ExampleService

    @Test
    fun `createExample should return saved example`() {
        // given
        val command = CreateExampleCommand("Test Name", "Test Description")
        val expectedModel = ExampleModel(id = 1L, name = "Test Name", description = "Test Description")
        
        whenever(examplePersistencePort.save(any())).thenReturn(expectedModel)

        // when
        val result = exampleService.createExample(command)

        // then
        assertEquals(expectedModel, result)
        verify(examplePersistencePort).save(any())
    }
}
```

## 4. 주의사항

### 4.1 패키지 네이밍
- `adapter.in.web` 패키지에서 백틱(`) 사용 시 주의
- 올바른 패키지명: `com.illsang.user.adapter.in.web`

### 4.2 의존성 주입
- 생성자 주입 사용 권장
- `@Autowired` 어노테이션 생략 가능

### 4.3 트랜잭션 관리
- Service 계층에서 트랜잭션 관리
- 읽기 전용 메서드에는 `@Transactional(readOnly = true)` 적용

### 4.4 예외 처리
- 비즈니스 로직 예외는 도메인 계층에서 정의
- 기술적 예외는 Infrastructure 계층에서 처리

## 5. 검증 체크리스트

- [ ] Entity가 BaseEntity를 상속하는가?
- [ ] Model이 BaseModel을 상속하는가?
- [ ] Mapper에 모든 변환 메서드가 있는가?
- [ ] Port 인터페이스가 정의되어 있는가?
- [ ] Repository가 JpaRepository를 상속하는가?
- [ ] Adapter가 Port를 구현하는가?
- [ ] Service에 적절한 트랜잭션 어노테이션이 있는가?
- [ ] Controller가 적절한 HTTP 상태 코드를 반환하는가?
- [ ] Request/Response DTO가 정의되어 있는가?
- [ ] 컴파일이 성공하는가? 
