# Persistence Adapter 통합 테스트 가이드

이 문서는 Spring Data JPA 기반 PersistenceAdapter(아웃풋 포트) 통합 테스트 작성 방법과 베스트 프랙티스를 안내합니다.

---

## 1. 목적
- PersistenceAdapter가 실제 DB(JPA/Hibernate)와 올바르게 연동되는지 검증
- Repository, Entity, Mapper, Model 계층의 end-to-end 동작 보장
- Mock이 아닌 실제 객체와 인메모리 DB(H2) 환경에서 테스트

---

## 2. 환경 및 어노테이션
- `@SpringBootTest`: Spring Context 전체 로딩, 실제 Bean 주입
- `@Transactional`: 각 테스트 후 롤백, DB 클린업 자동화
- `@Autowired`: Adapter, Repository 등 실제 객체 주입
- 테스트용 DB: H2 (application.yml에서 설정)

---

## 3. 테스트용 application.yml 예시
`src/test/resources/application.yml`
```yaml
spring:
  datasource:
    url: jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
    driver-class-name: org.h2.Driver
    username: sa
    password:
  jpa:
    hibernate:
      ddl-auto: create-drop
    show-sql: true
    properties:
      hibernate:
        format_sql: true
  h2:
    console:
      enabled: true
      path: /h2-console
logging:
  level:
    org.hibernate.SQL: debug
    org.hibernate.type: trace
```

---

## 4. 기본 테스트 구조
```kotlin
@SpringBootTest
@Transactional
class BannerPersistenceAdapterTest {
    @Autowired
    private lateinit var bannerPersistenceAdapter: BannerPersistenceAdapter

    @Autowired
    private lateinit var bannerRepository: BannerRepository

    @BeforeEach
    fun setUp() {
        bannerRepository.deleteAll()
    }

    @Test
    fun `배너를 저장하고 조회할 수 있다`() {
        val bannerModel = BannerModel(title = "테스트", activeYn = true)
        val saved = bannerPersistenceAdapter.save(bannerModel)
        val found = bannerPersistenceAdapter.findById(saved.id!!)
        assertNotNull(found)
        assertEquals("테스트", found?.title)
    }

    // ... 기타 CRUD, 조건별 조회, 존재 여부 등
}
```

---

## 5. 체크포인트 & 베스트 프랙티스
- **실제 DB에 저장/조회/삭제**: Mock 사용 금지, 실제 객체로 end-to-end 검증
- **@Transactional**: 각 테스트 후 롤백, DB 상태 보장
- **@BeforeEach에서 deleteAll()**: 테스트 독립성 보장
- **ID, 필드 값, 조건별 조회 등 다양한 케이스**를 테스트
- **엔티티/모델 매핑 오류, JPA 쿼리 오류 등도 조기 발견**
- **테스트 실행 속도**: H2 인메모리 DB로 빠른 실행

---

## 6. 예시: ImagePersistenceAdapterTest
```kotlin
@SpringBootTest
@Transactional
class ImagePersistenceAdapterTest {
    @Autowired
    private lateinit var imagePersistenceAdapter: ImagePersistenceAdapter
    @Autowired
    private lateinit var imageRepository: ImageRepository

    @BeforeEach
    fun setUp() { imageRepository.deleteAll() }

    @Test
    fun `이미지를 저장하고 조회할 수 있다`() {
        val imageModel = ImageModel(type = "PROFILE", name = "test.jpg", size = 1234L)
        val saved = imagePersistenceAdapter.save(imageModel)
        val found = imagePersistenceAdapter.findById(saved.id!!)
        assertNotNull(found)
        assertEquals("PROFILE", found?.type)
        assertEquals("test.jpg", found?.name)
        assertEquals(1234L, found?.size)
    }
}
```

---

## 7. 권장 사항
- **비즈니스 로직 없는 CRUD Adapter는 통합 테스트로 충분**
- **비즈니스 로직이 추가된 경우 단위 테스트도 병행**
- **테스트 커버리지 도구(JaCoCo 등)로 누락된 케이스 점검**

---

## 8. 참고
- Spring 공식 문서: [Testing the Persistence Layer](https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.testing)
- H2 DB: [https://www.h2database.com/](https://www.h2database.com/) 