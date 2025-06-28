package com.illsang.management.adapter.out.persistence

import com.illsang.management.domain.model.ImageModel
import com.illsang.management.adapter.out.persistence.repository.ImageRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional
class ImagePersistenceAdapterTest {

    @Autowired
    private lateinit var imagePersistenceAdapter: ImagePersistenceAdapter

    @Autowired
    private lateinit var imageRepository: ImageRepository

    @BeforeEach
    fun setUp() {
        imageRepository.deleteAll()
    }

    @Test
    @DisplayName("이미지를 저장하고 조회할 수 있다")
    fun `이미지를 저장하고 조회할 수 있다`() {
        // Given
        val imageModel = ImageModel(
            type = "PROFILE",
            name = "test.jpg",
            size = 1234L
        )

        // When
        val saved = imagePersistenceAdapter.save(imageModel)
        val found = imagePersistenceAdapter.findById(saved.id!!)

        // Then
        assertNotNull(found)
        assertEquals("PROFILE", found?.type)
        assertEquals("test.jpg", found?.name)
        assertEquals(1234L, found?.size)
    }

    @Test
    @DisplayName("이미지를 삭제하면 조회할 수 없다")
    fun `이미지를 삭제하면 조회할 수 없다`() {
        // Given
        val saved = imagePersistenceAdapter.save(
            ImageModel(type = "BANNER", name = "banner.png", size = 2048L)
        )

        // When
        imagePersistenceAdapter.deleteById(saved.id!!)
        val found = imagePersistenceAdapter.findById(saved.id!!)

        // Then
        assertNull(found)
    }

    @Test
    @DisplayName("여러 이미지를 저장하고 전체 조회할 수 있다")
    fun `여러 이미지를 저장하고 전체 조회할 수 있다`() {
        // Given
        imagePersistenceAdapter.save(ImageModel(type = "PROFILE", name = "a.jpg", size = 100L))
        imagePersistenceAdapter.save(ImageModel(type = "BANNER", name = "b.jpg", size = 200L))
        imagePersistenceAdapter.save(ImageModel(type = "ICON", name = "c.jpg", size = 300L))

        // When
        val all = imagePersistenceAdapter.findAll()

        // Then
        assertEquals(3, all.size)
        assertTrue(all.any { it.name == "a.jpg" })
        assertTrue(all.any { it.name == "b.jpg" })
        assertTrue(all.any { it.name == "c.jpg" })
    }

    @Test
    @DisplayName("타입별로 이미지를 조회할 수 있다")
    fun `타입별로 이미지를 조회할 수 있다`() {
        // Given
        imagePersistenceAdapter.save(ImageModel(type = "PROFILE", name = "a.jpg", size = 100L))
        imagePersistenceAdapter.save(ImageModel(type = "BANNER", name = "b.jpg", size = 200L))
        imagePersistenceAdapter.save(ImageModel(type = "PROFILE", name = "c.jpg", size = 300L))

        // When
        val profileImages = imagePersistenceAdapter.findByType("PROFILE")
        val bannerImages = imagePersistenceAdapter.findByType("BANNER")

        // Then
        assertTrue(profileImages.all { it.type == "PROFILE" })
        assertTrue(bannerImages.all { it.type == "BANNER" })
        assertEquals(2, profileImages.size)
        assertEquals(1, bannerImages.size)
    }

    @Test
    @DisplayName("존재 여부를 확인할 수 있다")
    fun `존재_여부를_확인할_수_있다`() {
        // Given
        val saved = imagePersistenceAdapter.save(ImageModel(type = "PROFILE", name = "a.jpg", size = 100L))

        // When
        val exists = imagePersistenceAdapter.existsById(saved.id!!)
        val notExists = imagePersistenceAdapter.existsById(99999L)

        // Then
        assertTrue(exists)
        assertFalse(notExists)
    }
}