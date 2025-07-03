package com.illsang.moduleuser.adapter.out.persistence

import com.illsang.moduleuser.adapter.out.persistence.repository.UserRepository
import com.illsang.moduleuser.adapter.out.persistence.repository.UserXpHistoryRepository
import com.illsang.moduleuser.domain.model.UserModel
import com.illsang.moduleuser.domain.model.UserStatus
import com.illsang.moduleuser.domain.model.UserXpHistoryModel
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
class UserXpHistoryPersistenceAdapterTest {

    @Autowired
    private lateinit var userXpHistoryPersistenceAdapter: UserXpHistoryPersistenceAdapter

    @Autowired
    private lateinit var userXpHistoryRepository: UserXpHistoryRepository

    @Autowired
    private lateinit var userRepository: UserRepository

    private lateinit var testUser: UserModel

    @BeforeEach
    fun setUp() {
        userXpHistoryRepository.deleteAll()
        userRepository.deleteAll()

        // 테스트용 사용자 생성
        testUser = userRepository.save(
            com.illsang.moduleuser.adapter.out.persistence.entity.UserEntity(
                email = "test@example.com",
                channel = "EMAIL",
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
    @DisplayName("사용자 XP 히스토리를 저장하고 조회할 수 있다")
    fun `사용자 XP 히스토리를 저장하고 조회할 수 있다`() {
        // Given
        val userXpHistoryModel = UserXpHistoryModel(
            userId = testUser.id!!,
            xpType = XpType.QUEST,
            point = 50
        )

        // When
        val saved = userXpHistoryPersistenceAdapter.save(userXpHistoryModel)
        val found = userXpHistoryPersistenceAdapter.findById(saved.id!!)

        // Then
        assertNotNull(found)
        assertEquals(testUser.id, found?.userId)
        assertEquals(XpType.QUEST, found?.xpType)
        assertEquals(50, found?.point)
    }

    @Test
    @DisplayName("사용자별 XP 히스토리 목록을 조회할 수 있다")
    fun `사용자별 XP 히스토리 목록을 조회할 수 있다`() {
        // Given
        userXpHistoryPersistenceAdapter.save(UserXpHistoryModel(
            userId = testUser.id!!,
            xpType = XpType.QUEST,
            point = 50
        ))
        userXpHistoryPersistenceAdapter.save(UserXpHistoryModel(
            userId = testUser.id!!,
            xpType = XpType.MISSION,
            point = 30
        ))

        // When
        val userXpHistories = userXpHistoryPersistenceAdapter.findByUserId(testUser.id!!)

        // Then
        assertEquals(2, userXpHistories.size)
        assertTrue(userXpHistories.any { it.xpType == XpType.QUEST && it.point == 50 })
        assertTrue(userXpHistories.any { it.xpType == XpType.MISSION && it.point == 30 })
    }

    @Test
    @DisplayName("사용자 ID와 XP 타입으로 히스토리를 조회할 수 있다")
    fun `사용자 ID와 XP 타입으로 히스토리를 조회할 수 있다`() {
        // Given
        userXpHistoryPersistenceAdapter.save(UserXpHistoryModel(
            userId = testUser.id!!,
            xpType = XpType.QUEST,
            point = 50
        ))
        userXpHistoryPersistenceAdapter.save(UserXpHistoryModel(
            userId = testUser.id!!,
            xpType = XpType.QUEST,
            point = 30
        ))
        userXpHistoryPersistenceAdapter.save(UserXpHistoryModel(
            userId = testUser.id!!,
            xpType = XpType.MISSION,
            point = 20
        ))

        // When
        val questHistories = userXpHistoryPersistenceAdapter.findByUserIdAndXpType(testUser.id!!, XpType.QUEST)
        val missionHistories = userXpHistoryPersistenceAdapter.findByUserIdAndXpType(testUser.id!!, XpType.MISSION)

        // Then
        assertEquals(2, questHistories.size)
        assertTrue(questHistories.all { it.xpType == XpType.QUEST })

        assertEquals(1, missionHistories.size)
        assertTrue(missionHistories.all { it.xpType == XpType.MISSION })
    }

    @Test
    @DisplayName("사용자 XP 히스토리를 삭제할 수 있다")
    fun `사용자 XP 히스토리를 삭제할 수 있다`() {
        // Given
        val saved = userXpHistoryPersistenceAdapter.save(UserXpHistoryModel(
            userId = testUser.id!!,
            xpType = XpType.QUEST,
            point = 50
        ))

        // When
        userXpHistoryPersistenceAdapter.deleteById(saved.id!!)
        val found = userXpHistoryPersistenceAdapter.findById(saved.id!!)

        // Then
        assertNull(found)
    }

    @Test
    @DisplayName("사용자 XP 히스토리 존재 여부를 확인할 수 있다")
    fun `사용자 XP 히스토리 존재 여부를 확인할 수 있다`() {
        // Given
        val saved = userXpHistoryPersistenceAdapter.save(UserXpHistoryModel(
            userId = testUser.id!!,
            xpType = XpType.QUEST,
            point = 50
        ))

        // When
        val exists = userXpHistoryPersistenceAdapter.existsById(saved.id!!)
        val notExists = userXpHistoryPersistenceAdapter.existsById(99999L)

        // Then
        assertTrue(exists)
        assertFalse(notExists)
    }

    @Test
    @DisplayName("존재하지 않는 사용자 ID로 조회하면 빈 리스트를 반환한다")
    fun `존재하지 않는 사용자 ID로 조회하면 빈 리스트를 반환한다`() {
        // When
        val userXpHistories = userXpHistoryPersistenceAdapter.findByUserId(99999L)

        // Then
        assertTrue(userXpHistories.isEmpty())
    }

    @Test
    @DisplayName("존재하지 않는 XP 타입으로 조회하면 빈 리스트를 반환한다")
    fun `존재하지 않는 XP 타입으로 조회하면 빈 리스트를 반환한다`() {
        // When
        val userXpHistories = userXpHistoryPersistenceAdapter.findByUserIdAndXpType(testUser.id!!, XpType.DAILY)

        // Then
        assertTrue(userXpHistories.isEmpty())
    }

    @Test
    @DisplayName("다양한 XP 타입의 히스토리를 저장할 수 있다")
    fun `다양한 XP 타입의 히스토리를 저장할 수 있다`() {
        // Given
        val questHistory = userXpHistoryPersistenceAdapter.save(UserXpHistoryModel(
            userId = testUser.id!!,
            xpType = XpType.QUEST,
            point = 50
        ))
        val missionHistory = userXpHistoryPersistenceAdapter.save(UserXpHistoryModel(
            userId = testUser.id!!,
            xpType = XpType.MISSION,
            point = 30
        ))
        val quizHistory = userXpHistoryPersistenceAdapter.save(UserXpHistoryModel(
            userId = testUser.id!!,
            xpType = XpType.DAILY,
            point = 20
        ))

        // When
        val foundQuest = userXpHistoryPersistenceAdapter.findById(questHistory.id!!)
        val foundMission = userXpHistoryPersistenceAdapter.findById(missionHistory.id!!)
        val foundQuiz = userXpHistoryPersistenceAdapter.findById(quizHistory.id!!)

        // Then
        assertEquals(XpType.QUEST, foundQuest?.xpType)
        assertEquals(XpType.MISSION, foundMission?.xpType)
        assertEquals(XpType.DAILY, foundQuiz?.xpType)
    }

    @Test
    @DisplayName("사용자별 XP 히스토리가 없으면 빈 리스트를 반환한다")
    fun `사용자별 XP 히스토리가 없으면 빈 리스트를 반환한다`() {
        // When
        val userXpHistories = userXpHistoryPersistenceAdapter.findByUserId(testUser.id!!)

        // Then
        assertTrue(userXpHistories.isEmpty())
    }

    @Test
    @DisplayName("존재하지 않는 사용자로 XP 히스토리 저장 시 예외가 발생한다")
    fun `존재하지 않는 사용자로 XP 히스토리 저장 시 예외가 발생한다`() {
        // Given
        val userXpHistoryModel = UserXpHistoryModel(
            userId = 99999L,
            xpType = XpType.QUEST,
            point = 50
        )

        // When & Then
        assertThrows(IllegalArgumentException::class.java) {
            userXpHistoryPersistenceAdapter.save(userXpHistoryModel)
        }
    }

    @Test
    @DisplayName("다양한 타겟 타입의 XP 히스토리를 저장할 수 있다")
    fun `다양한 타겟 타입의 XP 히스토리를 저장할 수 있다`() {
        // Given
        val questHistory = userXpHistoryPersistenceAdapter.save(UserXpHistoryModel(
            userId = testUser.id!!,
            xpType = XpType.QUEST,
            point = 50
        ))
        val missionHistory = userXpHistoryPersistenceAdapter.save(UserXpHistoryModel(
            userId = testUser.id!!,
            xpType = XpType.MISSION,
            point = 30
        ))

        // When
        val foundQuest = userXpHistoryPersistenceAdapter.findById(questHistory.id!!)
        val foundMission = userXpHistoryPersistenceAdapter.findById(missionHistory.id!!)

        // Then
        assertEquals(XpType.QUEST, foundQuest?.xpType)
        assertEquals(XpType.MISSION, foundMission?.xpType)
    }
}
