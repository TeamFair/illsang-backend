package com.illsang.management.adapter.out.persistence

import com.illsang.management.domain.model.BannerModel
import com.illsang.management.adapter.out.persistence.repository.BannerRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

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
    @DisplayName("배너를 저장하고 조회할 수 있다")
    fun `배너를 저장하고 조회할 수 있다`() {
        // Given
        val bannerModel = BannerModel(
            title = "테스트 배너",
            bannerImageId = 100L,
            description = "테스트 배너 설명",
            activeYn = true
        )

        // When
        val saved = bannerPersistenceAdapter.save(bannerModel)
        val found = bannerPersistenceAdapter.findById(saved.id!!)

        // Then
        assertNotNull(found)
        assertEquals("테스트 배너", found?.title)
        assertEquals(100L, found?.bannerImageId)
        assertEquals("테스트 배너 설명", found?.description)
        assertTrue(found?.activeYn == true)
    }

    @Test
    @DisplayName("배너를 삭제하면 조회할 수 없다")
    fun `배너를 삭제하면 조회할 수 없다`() {
        // Given
        val saved = bannerPersistenceAdapter.save(
            BannerModel(title = "삭제 배너", activeYn = true)
        )

        // When
        bannerPersistenceAdapter.deleteById(saved.id!!)
        val found = bannerPersistenceAdapter.findById(saved.id!!)

        // Then
        assertNull(found)
    }

    @Test
    @DisplayName("여러 배너를 저장하고 전체 조회할 수 있다")
    fun `여러 배너를 저장하고 전체 조회할 수 있다`() {
        // Given
        bannerPersistenceAdapter.save(BannerModel(title = "배너1", activeYn = true))
        bannerPersistenceAdapter.save(BannerModel(title = "배너2", activeYn = false))
        bannerPersistenceAdapter.save(BannerModel(title = "배너3", activeYn = true))

        // When
        val all = bannerPersistenceAdapter.findAll()

        // Then
        assertEquals(3, all.size)
        assertTrue(all.any { it.title == "배너1" })
        assertTrue(all.any { it.title == "배너2" })
        assertTrue(all.any { it.title == "배너3" })
    }

    @Test
    @DisplayName("활성/비활성 배너를 구분해서 조회할 수 있다")
    fun `활성_비활성_배너를_구분해서_조회할_수_있다`() {
        // Given
        bannerPersistenceAdapter.save(BannerModel(title = "active1", activeYn = true))
        bannerPersistenceAdapter.save(BannerModel(title = "inactive1", activeYn = false))
        bannerPersistenceAdapter.save(BannerModel(title = "active2", activeYn = true))

        // When
        val active = bannerPersistenceAdapter.findByActiveYn(true)
        val inactive = bannerPersistenceAdapter.findByActiveYn(false)

        // Then
        assertTrue(active.all { it.activeYn })
        assertTrue(inactive.all { !it.activeYn })
        assertTrue(active.size > 0)
        assertTrue(inactive.size > 0)
    }

    @Test
    @DisplayName("존재 여부를 확인할 수 있다")
    fun `존재_여부를_확인할_수_있다`() {
        // Given
        val saved = bannerPersistenceAdapter.save(BannerModel(title = "존재 배너", activeYn = true))

        // When
        val exists = bannerPersistenceAdapter.existsById(saved.id!!)
        val notExists = bannerPersistenceAdapter.existsById(99999L)

        // Then
        assertTrue(exists)
        assertFalse(notExists)
    }
}