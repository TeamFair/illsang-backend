package com.illsang.moduleuser.adapter.out.persistence

import com.illsang.moduleuser.adapter.out.persistence.entity.UserEntity
import com.illsang.moduleuser.adapter.out.persistence.repository.UserEmojiRepository
import com.illsang.moduleuser.adapter.out.persistence.repository.UserRepository
import com.illsang.moduleuser.domain.enums.OAuthProvider
import com.illsang.moduleuser.domain.model.UserEmojiModel
import com.illsang.moduleuser.domain.model.UserModel
import com.illsang.moduleuser.domain.model.UserStatus
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional
class UserEmojiPersistencePersistenceAdapterTest {

    @Autowired
    private lateinit var userEmojiPersistenceAdapter: UserEmojiPersistencePersistenceAdapter

    @Autowired
    private lateinit var userEmojiRepository: UserEmojiRepository

    @Autowired
    private lateinit var userRepository: UserRepository

    private lateinit var testUser: UserModel

    @BeforeEach
    fun setUp() {
        userEmojiRepository.deleteAll()
        userRepository.deleteAll()

        // 테스트용 사용자 생성
        testUser = userRepository.save(
            UserEntity(
                email = "test@example.com",
                channel = OAuthProvider.GOOGLE,
                nickname = "테스트유저",
                status = UserStatus.ACTIVE
            )
        ).let { UserModel(
            id = it.id,
            email = it.email,
            channel = it.channel,
            nickname = it.nickname,
            status = it.status
        ) }
    }

    @Test
    @DisplayName("사용자 이모지를 저장하고 조회할 수 있다")
    fun `사용자 이모지를 저장하고 조회할 수 있다`() {
        // Given
        val userEmojiModel = UserEmojiModel(
            userId = testUser.id!!,
            emojiId = 100L,
            isEquipped = false,
            targetId = 1L
        )

        // When
        val saved = userEmojiPersistenceAdapter.save(userEmojiModel)
        val found = userEmojiPersistenceAdapter.findById(saved.id!!)

        // Then
        assertNotNull(found)
        assertEquals(testUser.id, found?.userId)
        assertEquals(100L, found?.emojiId)
        assertEquals(false, found?.isEquipped)
    }

    @Test
    @DisplayName("사용자별 이모지 목록을 조회할 수 있다")
    fun `사용자별 이모지 목록을 조회할 수 있다`() {
        // Given
        userEmojiPersistenceAdapter.save(UserEmojiModel(
            userId = testUser.id!!,
            emojiId = 100L,
            isEquipped = false,
            targetId = 1L
        ))
        userEmojiPersistenceAdapter.save(UserEmojiModel(
            userId = testUser.id!!,
            emojiId = 101L,
            isEquipped = true,
            targetId = 2L
        ))

        // When
        val userEmojis = userEmojiPersistenceAdapter.findByUserId(testUser.id!!)

        // Then
        assertEquals(2, userEmojis.size)
        assertTrue(userEmojis.any { it.emojiId == 100L && !it.isEquipped })
        assertTrue(userEmojis.any { it.emojiId == 101L && it.isEquipped })
    }

    @Test
    @DisplayName("사용자 이모지를 삭제할 수 있다")
    fun `사용자 이모지를 삭제할 수 있다`() {
        // Given
        val saved = userEmojiPersistenceAdapter.save(UserEmojiModel(
            userId = testUser.id!!,
            emojiId = 100L,
            isEquipped = false,
            targetId = 1L
        ))

        // When
        userEmojiPersistenceAdapter.deleteById(saved.id!!)
        val found = userEmojiPersistenceAdapter.findById(saved.id!!)

        // Then
        assertNull(found)
    }

    @Test
    @DisplayName("사용자 이모지 존재 여부를 확인할 수 있다")
    fun `사용자 이모지 존재 여부를 확인할 수 있다`() {
        // Given
        val saved = userEmojiPersistenceAdapter.save(UserEmojiModel(
            userId = testUser.id!!,
            emojiId = 100L,
            isEquipped = false,
            targetId = 1L
        ))

        // When
        val exists = userEmojiPersistenceAdapter.existsById(saved.id!!)
        val notExists = userEmojiPersistenceAdapter.existsById(99999L)

        // Then
        assertTrue(exists)
        assertFalse(notExists)
    }

    @Test
    @DisplayName("존재하지 않는 사용자 ID로 조회하면 빈 리스트를 반환한다")
    fun `존재하지 않는 사용자 ID로 조회하면 빈 리스트를 반환한다`() {
        // When
        val userEmojis = userEmojiPersistenceAdapter.findByUserId(99999L)

        // Then
        assertTrue(userEmojis.isEmpty())
    }

    @Test
    @DisplayName("다양한 장착 상태의 이모지를 저장할 수 있다")
    fun `다양한 장착 상태의 이모지를 저장할 수 있다`() {
        // Given
        val equippedEmoji = userEmojiPersistenceAdapter.save(UserEmojiModel(
            userId = testUser.id!!,
            emojiId = 100L,
            isEquipped = true,
            targetId = 1L
        ))
        val unequippedEmoji = userEmojiPersistenceAdapter.save(UserEmojiModel(
            userId = testUser.id!!,
            emojiId = 101L,
            isEquipped = false,
            targetId = 2L
        ))

        // When
        val foundEquipped = userEmojiPersistenceAdapter.findById(equippedEmoji.id!!)
        val foundUnequipped = userEmojiPersistenceAdapter.findById(unequippedEmoji.id!!)

        // Then
        assertTrue(foundEquipped?.isEquipped == true)
        assertTrue(foundUnequipped?.isEquipped == false)
    }

    @Test
    @DisplayName("사용자별 이모지가 없으면 빈 리스트를 반환한다")
    fun `사용자별 이모지가 없으면 빈 리스트를 반환한다`() {
        // When
        val userEmojis = userEmojiPersistenceAdapter.findByUserId(testUser.id!!)

        // Then
        assertTrue(userEmojis.isEmpty())
    }
}
