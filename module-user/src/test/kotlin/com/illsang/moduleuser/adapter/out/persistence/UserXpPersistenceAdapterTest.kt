package com.illsang.moduleuser.adapter.out.persistence

import com.illsang.moduleuser.adapter.out.persistence.entity.UserEntity
import com.illsang.moduleuser.adapter.out.persistence.repository.UserRepository
import com.illsang.moduleuser.adapter.out.persistence.repository.UserXpRepository
import com.illsang.moduleuser.domain.enums.OAuthProvider
import com.illsang.moduleuser.domain.model.UserModel
import com.illsang.moduleuser.domain.model.UserStatus
import com.illsang.moduleuser.domain.model.UserXpModel
import com.illsang.moduleuser.domain.model.XpType
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional
class UserXpPersistenceAdapterTest {

    @Autowired
    private lateinit var userXpPersistenceAdapter: UserXpPersistenceAdapter

    @Autowired
    private lateinit var userXpRepository: UserXpRepository

    @Autowired
    private lateinit var userRepository: UserRepository

    private lateinit var testUser: UserModel

    @BeforeEach
    fun setUp() {
        userXpRepository.deleteAll()
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
    @DisplayName("사용자 XP를 저장하고 조회할 수 있다")
    fun `사용자 XP를 저장하고 조회할 수 있다`() {
        // Given
        val userXpModel = UserXpModel(
            userId = testUser.id!!,
            xpType = XpType.QUEST,
            point = 100
        )

        // When
        val saved = userXpPersistenceAdapter.save(userXpModel)
        val found = userXpPersistenceAdapter.findById(saved.id!!)

        // Then
        assertNotNull(found)
        assertEquals(testUser.id, found?.userId)
        assertEquals(XpType.QUEST, found?.xpType)
        assertEquals(100, found?.point)
    }

    @Test
    @DisplayName("사용자별 XP 목록을 조회할 수 있다")
    fun `사용자별 XP 목록을 조회할 수 있다`() {
        // Given
        userXpPersistenceAdapter.save(UserXpModel(
            userId = testUser.id!!,
            xpType = XpType.QUEST,
            point = 100
        ))
        userXpPersistenceAdapter.save(UserXpModel(
            userId = testUser.id!!,
            xpType = XpType.MISSION,
            point = 200
        ))

        // When
        val userXps = userXpPersistenceAdapter.findByUserId(testUser.id!!)

        // Then
        assertEquals(2, userXps.size)
        assertTrue(userXps.any { it.xpType == XpType.QUEST && it.point == 100 })
        assertTrue(userXps.any { it.xpType == XpType.MISSION && it.point == 200 })
    }

    @Test
    @DisplayName("사용자 ID와 XP 타입으로 조회할 수 있다")
    fun `사용자 ID와 XP 타입으로 조회할 수 있다`() {
        // Given
        userXpPersistenceAdapter.save(UserXpModel(
            userId = testUser.id!!,
            xpType = XpType.QUEST,
            point = 100
        ))
        userXpPersistenceAdapter.save(UserXpModel(
            userId = testUser.id!!,
            xpType = XpType.MISSION,
            point = 200
        ))

        // When
        val questXp = userXpPersistenceAdapter.findByUserIdAndXpType(testUser.id!!, XpType.QUEST)
        val missionXp = userXpPersistenceAdapter.findByUserIdAndXpType(testUser.id!!, XpType.MISSION)

        // Then
        assertNotNull(questXp)
        assertEquals(XpType.QUEST, questXp?.xpType)
        assertEquals(100, questXp?.point)

        assertNotNull(missionXp)
        assertEquals(XpType.MISSION, missionXp?.xpType)
        assertEquals(200, missionXp?.point)
    }

    @Test
    @DisplayName("사용자 XP를 삭제할 수 있다")
    fun `사용자 XP를 삭제할 수 있다`() {
        // Given
        val saved = userXpPersistenceAdapter.save(UserXpModel(
            userId = testUser.id!!,
            xpType = XpType.QUEST,
            point = 100
        ))

        // When
        userXpPersistenceAdapter.deleteById(saved.id!!)
        val found = userXpPersistenceAdapter.findById(saved.id!!)

        // Then
        assertNull(found)
    }

    @Test
    @DisplayName("사용자 XP 존재 여부를 확인할 수 있다")
    fun `사용자 XP 존재 여부를 확인할 수 있다`() {
        // Given
        val saved = userXpPersistenceAdapter.save(UserXpModel(
            userId = testUser.id!!,
            xpType = XpType.QUEST,
            point = 100
        ))

        // When
        val exists = userXpPersistenceAdapter.existsById(saved.id!!)
        val notExists = userXpPersistenceAdapter.existsById(99999L)

        // Then
        assertTrue(exists)
        assertFalse(notExists)
    }

    @Test
    @DisplayName("존재하지 않는 사용자 ID로 조회하면 빈 리스트를 반환한다")
    fun `존재하지 않는 사용자 ID로 조회하면 빈 리스트를 반환한다`() {
        // When
        val userXps = userXpPersistenceAdapter.findByUserId(99999L)

        // Then
        assertTrue(userXps.isEmpty())
    }

    @Test
    @DisplayName("존재하지 않는 XP 타입으로 조회하면 null을 반환한다")
    fun `존재하지 않는 XP 타입으로 조회하면 null을 반환한다`() {
        // When
        val userXp = userXpPersistenceAdapter.findByUserIdAndXpType(testUser.id!!, XpType.DAILY)

        // Then
        assertNull(userXp)
    }

    @Test
    @DisplayName("다양한 XP 타입을 저장할 수 있다")
    fun `다양한 XP 타입을 저장할 수 있다`() {
        // Given
        val questXp = userXpPersistenceAdapter.save(UserXpModel(
            userId = testUser.id!!,
            xpType = XpType.QUEST,
            point = 100
        ))
        val missionXp = userXpPersistenceAdapter.save(UserXpModel(
            userId = testUser.id!!,
            xpType = XpType.MISSION,
            point = 200
        ))
        val quizXp = userXpPersistenceAdapter.save(UserXpModel(
            userId = testUser.id!!,
            xpType = XpType.DAILY,
            point = 50
        ))

        // When
        val foundQuest = userXpPersistenceAdapter.findById(questXp.id!!)
        val foundMission = userXpPersistenceAdapter.findById(missionXp.id!!)
        val foundQuiz = userXpPersistenceAdapter.findById(quizXp.id!!)

        // Then
        assertEquals(XpType.QUEST, foundQuest?.xpType)
        assertEquals(XpType.MISSION, foundMission?.xpType)
        assertEquals(XpType.DAILY, foundQuiz?.xpType)
    }

    @Test
    @DisplayName("사용자별 XP가 없으면 빈 리스트를 반환한다")
    fun `사용자별 XP가 없으면 빈 리스트를 반환한다`() {
        // When
        val userXps = userXpPersistenceAdapter.findByUserId(testUser.id!!)

        // Then
        assertTrue(userXps.isEmpty())
    }

    @Test
    @DisplayName("존재하지 않는 사용자로 XP 저장 시 예외가 발생한다")
    fun `존재하지 않는 사용자로 XP 저장 시 예외가 발생한다`() {
        // Given
        val userXpModel = UserXpModel(
            userId = 99999L,
            xpType = XpType.QUEST,
            point = 100
        )

        // When & Then
        assertThrows(IllegalArgumentException::class.java) {
            userXpPersistenceAdapter.save(userXpModel)
        }
    }
}
