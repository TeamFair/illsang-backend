package com.illsang.moduleuser.adapter.out.persistence

import com.illsang.moduleuser.adapter.out.persistence.repository.UserRepository
import com.illsang.moduleuser.domain.enums.OAuthProvider
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
class UserPersistenceAdapterTest {

    @Autowired
    private lateinit var userPersistenceAdapter: UserPersistenceAdapter

    @Autowired
    private lateinit var userRepository: UserRepository

    @BeforeEach
    fun setUp() {
        userRepository.deleteAll()
    }

    @Test
    @DisplayName("사용자를 저장하고 조회할 수 있다")
    fun `사용자를 저장하고 조회할 수 있다`() {
        // Given
        val userModel = UserModel(
            email = "test@example.com",
            channel = OAuthProvider.GOOGLE,
            nickname = "테스트유저",
            status = UserStatus.ACTIVE
        )

        // When
        val saved = userPersistenceAdapter.save(userModel)
        val found = userPersistenceAdapter.findById(saved.id!!)

        // Then
        assertNotNull(found)
        assertEquals("test@example.com", found?.email)
        assertEquals("EMAIL", found?.channel)
        assertEquals("테스트유저", found?.nickname)
        assertEquals(UserStatus.ACTIVE, found?.status)
    }

    @Test
    @DisplayName("이메일로 사용자를 조회할 수 있다")
    fun `이메일로 사용자를 조회할 수 있다`() {
        // Given
        val userModel = UserModel(
            email = "test@example.com",
            channel = OAuthProvider.GOOGLE,
            nickname = "테스트유저",
            status = UserStatus.ACTIVE
        )
        val saved = userPersistenceAdapter.save(userModel)

        // When
        val found = userPersistenceAdapter.findByEmail("test@example.com")

        // Then
        assertNotNull(found)
        assertEquals(saved.id, found?.id)
        assertEquals("test@example.com", found?.email)
    }

    @Test
    @DisplayName("존재하지 않는 이메일로 조회하면 null을 반환한다")
    fun `존재하지 않는 이메일로 조회하면 null을 반환한다`() {
        // When
        val found = userPersistenceAdapter.findByEmail("nonexistent@example.com")

        // Then
        assertNull(found)
    }

    @Test
    @DisplayName("모든 사용자를 조회할 수 있다")
    fun `모든 사용자를 조회할 수 있다`() {
        // Given
        userPersistenceAdapter.save(UserModel(
            email = "user1@example.com",
            channel = OAuthProvider.GOOGLE,
            nickname = "유저1",
            status = UserStatus.ACTIVE
        ))
        userPersistenceAdapter.save(UserModel(
            email = "user2@example.com",
            channel = OAuthProvider.GOOGLE,
            nickname = "유저2",
            status = UserStatus.INACTIVE
        ))

        // When
        val all = userPersistenceAdapter.findAll()

        // Then
        assertEquals(2, all.size)
        assertTrue(all.any { it.email == "user1@example.com" })
        assertTrue(all.any { it.email == "user2@example.com" })
    }

    @Test
    @DisplayName("사용자를 삭제할 수 있다")
    fun `사용자를 삭제할 수 있다`() {
        // Given
        val saved = userPersistenceAdapter.save(UserModel(
            email = "delete@example.com",
            channel = OAuthProvider.GOOGLE,
            nickname = "삭제유저",
            status = UserStatus.ACTIVE
        ))

        // When
        userPersistenceAdapter.deleteById(saved.id!!)
        val found = userPersistenceAdapter.findById(saved.id!!)

        // Then
        assertNull(found)
    }

    @Test
    @DisplayName("사용자 존재 여부를 확인할 수 있다")
    fun `사용자 존재 여부를 확인할 수 있다`() {
        // Given
        val saved = userPersistenceAdapter.save(UserModel(
            email = "exists@example.com",
            channel = OAuthProvider.GOOGLE,
            nickname = "존재유저",
            status = UserStatus.ACTIVE
        ))

        // When
        val exists = userPersistenceAdapter.existsById(saved.id!!)
        val notExists = userPersistenceAdapter.existsById(99999L)

        // Then
        assertTrue(exists)
        assertFalse(notExists)
    }

    @Test
    @DisplayName("이메일 존재 여부를 확인할 수 있다")
    fun `이메일 존재 여부를 확인할 수 있다`() {
        // Given
        userPersistenceAdapter.save(UserModel(
            email = "exists@example.com",
            channel = OAuthProvider.GOOGLE,
            nickname = "존재유저",
            status = UserStatus.ACTIVE
        ))

        // When
        val exists = userPersistenceAdapter.existsByEmail("exists@example.com")
        val notExists = userPersistenceAdapter.existsByEmail("nonexistent@example.com")

        // Then
        assertTrue(exists)
        assertFalse(notExists)
    }

    @Test
    @DisplayName("사용자가 없으면 빈 리스트를 반환한다")
    fun `사용자가 없으면 빈 리스트를 반환한다`() {
        // When
        val all = userPersistenceAdapter.findAll()

        // Then
        assertTrue(all.isEmpty())
    }

    @Test
    @DisplayName("다양한 상태의 사용자를 저장할 수 있다")
    fun `다양한 상태의 사용자를 저장할 수 있다`() {
        // Given
        val activeUser = userPersistenceAdapter.save(UserModel(
            email = "active@example.com",
            channel = OAuthProvider.GOOGLE,
            nickname = "활성유저",
            status = UserStatus.ACTIVE
        ))
        val dormantUser = userPersistenceAdapter.save(UserModel(
            email = "dormant@example.com",
            channel = OAuthProvider.GOOGLE,
            nickname = "휴면유저",
            status = UserStatus.INACTIVE
        ))

        // When
        val foundActive = userPersistenceAdapter.findById(activeUser.id!!)
        val foundDormant = userPersistenceAdapter.findById(dormantUser.id!!)

        // Then
        assertEquals(UserStatus.ACTIVE, foundActive?.status)
        assertEquals(UserStatus.INACTIVE, foundDormant?.status)
    }
}
