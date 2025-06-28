# Controller 테스트 가이드

## 개요

이 문서는 Spring Boot 애플리케이션에서 Controller 계층을 테스트하는 방법에 대해 설명합니다. 
DDD, Hexagonal Architecture, Clean Architecture 패턴을 따르는 프로젝트에서 Controller 테스트를 작성하는 방법을 다룹니다.

## 테스트 환경 설정

### 1. 의존성 설정

```kotlin
// build.gradle.kts
dependencies {
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.mockito:mockito-core")
    testImplementation("org.mockito:mockito-junit-jupiter")
    testImplementation("com.fasterxml.jackson.module:jackson-module-kotlin")
}
```

### 2. 테스트 어노테이션

```kotlin
@AutoConfigureMockMvc(addFilters = false)  // 보안 필터 비활성화
@WebMvcTest(YourController::class)         // 특정 Controller만 테스트
class YourControllerTest {
    // 테스트 코드
}
```

## 기본 테스트 구조

### 1. 테스트 클래스 기본 구조

```kotlin
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(YourController::class)
class YourControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockitoBean
    private lateinit var yourService: YourService

    private val objectMapper = jacksonObjectMapper()

    // 테스트 메서드들...
}
```

### 2. MockMvc 설정

- `@Autowired private lateinit var mockMvc: MockMvc`: HTTP 요청을 시뮬레이션
- `@MockitoBean private lateinit var yourService: YourService`: 서비스 계층 모킹
- `private val objectMapper = jacksonObjectMapper()`: JSON 직렬화/역직렬화

## HTTP 메서드별 테스트 방법

### 1. GET 요청 테스트

#### 단순 GET 요청
```kotlin
@Test
@DisplayName("GET /api/resource - 리소스 목록 조회 성공")
fun `리소스를 조회하면 200 OK와 리스트를 반환한다`() {
    // Given
    val resources = listOf(
        ResourceModel(id = 1L, name = "Resource 1"),
        ResourceModel(id = 2L, name = "Resource 2")
    )
    given(yourService.getAllResources()).willReturn(resources)

    // When & Then
    mockMvc.get("/api/resource")
        .andExpect {
            status { isOk() }
            content { contentType(MediaType.APPLICATION_JSON) }
            jsonPath("$.size()") { value(2) }
            jsonPath("$[0].id") { value(1) }
            jsonPath("$[0].name") { value("Resource 1") }
        }
}
```

#### Path Variable이 있는 GET 요청
```kotlin
@Test
@DisplayName("GET /api/resource/{id} - 리소스 단건 조회 성공")
fun `리소스를 ID로 조회하면 200 OK와 상세 정보를 반환한다`() {
    // Given
    val resource = ResourceModel(id = 1L, name = "Resource 1")
    given(yourService.getResource(1L)).willReturn(resource)

    // When & Then
    mockMvc.get("/api/resource/1")
        .andExpect {
            status { isOk() }
            content { contentType(MediaType.APPLICATION_JSON) }
            jsonPath("$.id") { value(1) }
            jsonPath("$.name") { value("Resource 1") }
        }
}
```

### 2. POST 요청 테스트

```kotlin
@Test
@DisplayName("POST /api/resource - 리소스 생성 성공")
fun `리소스를 생성하면 201 CREATED와 생성된 리소스를 반환한다`() {
    // Given
    val command = CreateResourceCommand(
        name = "New Resource",
        description = "Description"
    )
    val createdResource = ResourceModel(
        id = 1L,
        name = command.name,
        description = command.description
    )
    given(yourService.createResource(command)).willReturn(createdResource)

    // When & Then
    mockMvc.post("/api/resource") {
        contentType = MediaType.APPLICATION_JSON
        content = objectMapper.writeValueAsString(command)
    }.andExpect {
        status { isCreated() }
        content { contentType(MediaType.APPLICATION_JSON) }
        jsonPath("$.id") { value(1) }
        jsonPath("$.name") { value(command.name) }
    }
}
```

### 3. PUT 요청 테스트

```kotlin
@Test
@DisplayName("PUT /api/resource/{id} - 리소스 수정 성공")
fun `리소스를 수정하면 200 OK와 수정된 리소스를 반환한다`() {
    // Given
    val updateCommand = UpdateResourceCommand(
        id = 1L,
        name = "Updated Resource",
        description = "Updated Description"
    )
    val updatedResource = ResourceModel(
        id = 1L,
        name = updateCommand.name!!,
        description = updateCommand.description!!
    )
    given(yourService.updateResource(updateCommand)).willReturn(updatedResource)

    // When & Then
    mockMvc.put("/api/resource/1") {
        contentType = MediaType.APPLICATION_JSON
        content = objectMapper.writeValueAsString(updateCommand.copy(id = 1L))
    }.andExpect {
        status { isOk() }
        content { contentType(MediaType.APPLICATION_JSON) }
        jsonPath("$.id") { value(1) }
        jsonPath("$.name") { value("Updated Resource") }
    }
}
```

### 4. DELETE 요청 테스트

```kotlin
@Test
@DisplayName("DELETE /api/resource/{id} - 리소스 삭제 성공")
fun `리소스를 삭제하면 200 OK와 성공 메시지를 반환한다`() {
    // Given
    doNothing().`when`(yourService).deleteResource(1L)

    // When & Then
    mockMvc.delete("/api/resource/1")
        .andExpect {
            status { isOk() }
            content { contentType(MediaType.APPLICATION_JSON) }
        }
}
```

## Mockito 사용법

### 1. given() - 메서드 반환값 설정

```kotlin
// 단일 객체 반환
given(yourService.getResource(1L)).willReturn(resource)

// 리스트 반환
given(yourService.getAllResources()).willReturn(resources)

// null 반환
given(yourService.getResource(999L)).willReturn(null)

// 예외 발생
given(yourService.getResource(999L)).willThrow(ResourceNotFoundException())
```

### 2. doNothing() - void 메서드 모킹

```kotlin
// void 메서드 모킹
doNothing().`when`(yourService).deleteResource(1L)

// 예외 발생하는 void 메서드
doThrow(ResourceNotFoundException()).`when`(yourService).deleteResource(999L)
```

## JSON 응답 검증

### 1. jsonPath() 사용법

```kotlin
mockMvc.get("/api/resource")
    .andExpect {
        // 배열 크기 검증
        jsonPath("$.size()") { value(2) }
        
        // 배열 요소 검증
        jsonPath("$[0].id") { value(1) }
        jsonPath("$[0].name") { value("Resource 1") }
        
        // 중첩 객체 검증
        jsonPath("$.data.id") { value(1) }
        jsonPath("$.data.name") { value("Resource 1") }
        
        // 문자열 검증
        jsonPath("$.message") { value("Success") }
        
        // 숫자 검증
        jsonPath("$.count") { value(10) }
        
        // 불린 검증
        jsonPath("$.active") { value(true) }
    }
```

### 2. 복잡한 JSON 구조 검증

```kotlin
mockMvc.get("/api/resource")
    .andExpect {
        // 배열의 모든 요소가 특정 조건을 만족하는지 검증
        jsonPath("$[*].id").exists()
        jsonPath("$[*].name").exists()
        
        // 특정 조건을 만족하는 요소 검증
        jsonPath("$[?(@.active == true)].size()") { value(2) }
        jsonPath("$[?(@.type == 'BANNER')].size()") { value(1) }
    }
```

## HTTP 상태 코드 검증

```kotlin
mockMvc.get("/api/resource")
    .andExpect {
        // 성공 응답
        status { isOk() }           // 200
        status { isCreated() }      // 201
        status { isAccepted() }     // 202
        
        // 클라이언트 오류
        status { isBadRequest() }   // 400
        status { isUnauthorized() } // 401
        status { isForbidden() }    // 403
        status { isNotFound() }     // 404
        
        // 서버 오류
        status { isInternalServerError() } // 500
    }
```

## Content-Type 검증

```kotlin
mockMvc.get("/api/resource")
    .andExpect {
        // JSON 응답 검증
        content { contentType(MediaType.APPLICATION_JSON) }
        
        // XML 응답 검증
        content { contentType(MediaType.APPLICATION_XML) }
        
        // 특정 인코딩 검증
        content { contentType("application/json;charset=UTF-8") }
    }
```

## 실제 예제: BannerController 테스트

```kotlin
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(BannerController::class)
class BannerControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockitoBean
    private lateinit var bannerService: BannerService

    private val objectMapper = jacksonObjectMapper()

    @Test
    @DisplayName("POST /api/v1/banners - 배너 생성 성공")
    fun `배너를 생성하면 201 CREATED와 생성된 배너를 반환한다`() {
        // Given
        val command = CreateBannerCommand(
            title = "이벤트 배너",
            bannerImageId = 1001L,
            description = "여름 프로모션",
            activeYn = true
        )
        val createdBanner = BannerModel(
            id = 1L,
            title = command.title,
            bannerImageId = command.bannerImageId,
            description = command.description,
            activeYn = command.activeYn
        )
        given(bannerService.createBanner(command)).willReturn(createdBanner)

        // When & Then
        mockMvc.post("/api/v1/banners") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(command)
        }.andExpect {
            status { isCreated() }
            content { contentType(MediaType.APPLICATION_JSON) }
            jsonPath("$.id") { value(1) }
            jsonPath("$.title") { value(command.title) }
        }
    }

    @Test
    @DisplayName("GET /api/v1/banners - 배너 목록 조회 성공")
    fun `배너 목록을 조회하면 200 OK와 리스트를 반환한다`() {
        // Given
        val banners = listOf(
            BannerModel(
                id = 1L,
                title = "이벤트 배너",
                bannerImageId = 1001L,
                description = "여름 프로모션",
                activeYn = true
            ),
            BannerModel(
                id = 2L,
                title = "공지사항 배너",
                bannerImageId = 1002L,
                description = "점검 안내",
                activeYn = false
            )
        )
        given(bannerService.getAllBanners()).willReturn(banners)

        // When & Then
        mockMvc.get("/api/v1/banners")
            .andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
                jsonPath("$.size()") { value(2) }
                jsonPath("$[0].id") { value(1) }
                jsonPath("$[0].title") { value("이벤트 배너") }
                jsonPath("$[1].id") { value(2) }
                jsonPath("$[1].title") { value("공지사항 배너") }
            }
    }
}
```

## 테스트 실행 방법

### 1. 전체 테스트 실행
```bash
./gradlew test
```

### 2. 특정 모듈 테스트 실행
```bash
./gradlew :module-management:test
```

### 3. 특정 테스트 클래스 실행
```bash
./gradlew :module-management:test --tests "*BannerControllerTest*"
```

### 4. 특정 테스트 메서드 실행
```bash
./gradlew :module-management:test --tests "*BannerControllerTest.createBanner*"
```

## 모범 사례

### 1. 테스트 메서드 명명 규칙
- `한국어_설명_테스트_결과` 형태로 작성
- 예: `배너를_생성하면_201_CREATED와_생성된_배너를_반환한다`

### 2. Given-When-Then 패턴
```kotlin
@Test
fun `테스트_시나리오`() {
    // Given: 테스트 데이터 준비
    val command = CreateResourceCommand(...)
    given(service.createResource(command)).willReturn(createdResource)
    
    // When & Then: HTTP 요청 및 응답 검증
    mockMvc.post("/api/resource") { ... }
        .andExpect { ... }
}
```

### 3. 테스트 격리
- 각 테스트는 독립적으로 실행될 수 있어야 함
- `@MockitoBean`을 사용하여 의존성을 모킹
- 테스트 데이터는 각 테스트에서 새로 생성

### 4. 명확한 검증
- HTTP 상태 코드, Content-Type, 응답 본문을 모두 검증
- jsonPath를 사용하여 응답 구조를 정확히 검증
- 예상되는 모든 필드를 검증

## 주의사항

1. **실제 데이터베이스 사용 금지**: Controller 테스트에서는 실제 데이터베이스를 사용하지 말고 Mock을 사용
2. **서비스 계층 모킹**: Controller는 서비스 계층에 의존하므로 반드시 모킹
3. **보안 필터 비활성화**: `@AutoConfigureMockMvc(addFilters = false)` 사용
4. **JSON 직렬화**: `jacksonObjectMapper()`를 사용하여 객체를 JSON으로 변환

## 결론

Controller 테스트는 애플리케이션의 HTTP 인터페이스가 올바르게 동작하는지 검증하는 중요한 역할을 합니다. 
이 가이드를 따라 체계적이고 견고한 Controller 테스트를 작성하여 애플리케이션의 안정성을 보장할 수 있습니다. 