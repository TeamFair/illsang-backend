package com.illsang.user.service

import com.illsang.common.enums.TitleGrade
import com.illsang.common.enums.TitleId
import com.illsang.common.event.user.title.GetTitleInfoEvent
import com.illsang.user.domain.entity.UserTitleEntity
import com.illsang.user.domain.model.UserTitleForPointModel
import com.illsang.user.domain.model.UserTitleModel
import com.illsang.user.dto.request.CreateUserTitleRequest
import com.illsang.user.repository.UserTitleRepository
import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional(readOnly = true)
class UserTitleService(
    private val userTitleRepository: UserTitleRepository,
    private val eventPublisher: ApplicationEventPublisher,
    private val userService: UserService,
    private val userPointService: UserPointService,
) {

    fun findById(id: Long): UserTitleEntity {
        return userTitleRepository.findByIdOrNull(id)
            ?: throw IllegalArgumentException("UserTitle not found with id: $id")
    }

    fun findByUserIdAndId(id: Long, userId: String): UserTitleEntity {
        return userTitleRepository.findByUserIdAndId(userId, id)
            ?: throw IllegalArgumentException("UserTitle not found with id and userId: $id , $userId")
    }

    fun getTitlesByUserId(userId: String): List<UserTitleModel> {
        val userTitles = userTitleRepository.findAllByUserId(userId)
        return userTitles.map { UserTitleModel.from(it) }
    }

    fun getTitle(id: Long): UserTitleModel {
        val userTitle = this.findById(id)
        return UserTitleModel.from(userTitle)
    }

    fun getUnreadTitle(userId: String) : List<UserTitleModel>{
        val userTitle = userTitleRepository.findAllByUserIdAndReadYnIsFalse(userId)
        return userTitle.map { UserTitleModel.from(it) }
    }

    fun getAllLegendTitle(pageable: Pageable, titleId: String?) : Page<UserTitleForPointModel> {
        val userTitles = userTitleRepository.findAllByTitleId(pageable, titleId)
        return userTitles.map { userTitle ->
            val point = userPointService.findUserTotalPoint(userTitle.user.id!!)
            val user = userService.findById(userTitle.user.id!!)
            UserTitleForPointModel.from(userTitle, user, point)
        }
    }

    @Transactional
    fun updateReadStatus(id: Long) {
        val userTitle = this.findById(id)
        userTitle.updateReadYn()
    }

    @Transactional
    fun deleteUserTitle(id: Long) {
        val userTitle = this.findById(id)
        userTitleRepository.delete(userTitle)
    }

    @Transactional
    fun createUserTitle(
        userId: String,
        titleId: String,
    ) {
        val titleEvent = GetTitleInfoEvent(titleId)
        this.eventPublisher.publishEvent(titleEvent)

        val titleName = titleEvent.response.titleName
        val titleGrade = titleEvent.response.titleGrade
        val titleType = titleEvent.response.titleType

        val user = userService.findById(userId)
        val existingTitles = userTitleRepository.existsByUserIdAndTitleId(userId, titleId)
        if (!existingTitles) {
            val newTitle = UserTitleEntity(
                titleId = titleId,
                titleName = titleName,
                titleGrade = titleGrade,
                titleType = titleType,
                user = user,
            )
            userTitleRepository.save(newTitle)
            user.updateTitle(newTitle)
        }
    }

    @Transactional
    fun createUserTitles(requests: List<CreateUserTitleRequest>) {
        if (requests.isEmpty()) return

        // 1. 요청 자체에서 중복 제거
        val distinctRequests = requests.distinctBy { it.userId to it.titleId }

        // 2. titleId별 이벤트 미리 발행
        val titleMap: Map<String, GetTitleInfoEvent> = distinctRequests
            .map { it.titleId }
            .distinct()
            .associateWith { titleId ->
                val event = GetTitleInfoEvent(titleId)
                eventPublisher.publishEvent(event)
                event
            }

        // 3. 기존 데이터 조회
        val userIds = distinctRequests.map { it.userId }.distinct()
        val existing = userTitleRepository.findByUserIdIn(userIds)
            .map { it.user.id to it.titleId }
            .toSet()

        // 4. 신규 UserTitleEntity 생성 (중복은 제외)
        val newTitles = distinctRequests.mapNotNull { req ->
            val user = userService.findById(req.userId)
            val event = titleMap[req.titleId] ?: return@mapNotNull null
            val titleResponse = event.response

            // DB에 이미 있으면 skip
            if (existing.contains(req.userId to req.titleId)) return@mapNotNull null

            UserTitleEntity(
                user = user,
                titleId = req.titleId,
                titleName = titleResponse.titleName,
                titleGrade = titleResponse.titleGrade,
                titleType = titleResponse.titleType
            ).also { user.updateTitle(it) }
        }

        // 5. 일괄 저장
        if (newTitles.isNotEmpty()) {
            userTitleRepository.saveAll(newTitles)
        }
    }

    fun updateUserTitle(userTitleId: Long, userId: String) {
        val user = userService.findById(userId)

        when (userTitleId) {
            0L -> user.updateTitle(null)
            else -> {
                val userTitle = findByUserIdAndId(userTitleId, userId)
                user.updateTitle(userTitle)
            }
        }
    }

    fun getTitleIdForQuestComplete(maxStreak: Int): String? {
        val titleId = when {
            maxStreak >= 360 -> TitleId.TITLE_360_DAYS.titleId
            maxStreak >= 240 -> TitleId.TITLE_TWO_FORTY_DAYS.titleId
            maxStreak >= 120 -> TitleId.TITLE_ONE_TWENTY_DAYS.titleId
            maxStreak >= 60 -> TitleId.TITLE_SIXTY_DAYS.titleId
            maxStreak >= 30 -> TitleId.TITLE_THIRTY_DAYS.titleId
            maxStreak >= 14 -> TitleId.TITLE_FOURTEEN_DAYS.titleId
            maxStreak >= 7 -> TitleId.TITLE_SEVEN_DAYS.titleId
            maxStreak >= 4 -> TitleId.TITLE_FOUR_DAYS.titleId
            maxStreak >= 2 -> TitleId.TITLE_ONE_DAY.titleId
            maxStreak >= 1 -> TitleId.TITLE_FIRST_STEP.titleId
            else -> null
        }

        return titleId ?: throw IllegalArgumentException("Title not found for streak: $maxStreak")
    }

    // 메트로/상권 랭킹 -> 타이틀 매핑
    fun mapMetroTitleId(rank: Long): String? = when {
        rank == 1L -> TitleId.TITLE_METRO_1.titleId
        rank == 2L -> TitleId.TITLE_METRO_2.titleId
        rank == 3L -> TitleId.TITLE_METRO_3.titleId
        rank in 4L..10L -> TitleId.TITLE_METRO_4_TO_10.titleId
        else -> null
    }

    // 시즌 기여 랭킹 -> 타이틀 매핑
    fun mapContribTitleId(rank: Long): String? = when {
        rank == 1L -> TitleId.TITLE_CONTRIB_1.titleId
        rank == 2L -> TitleId.TITLE_CONTRIB_2.titleId
        rank == 3L -> TitleId.TITLE_CONTRIB_3.titleId
        rank in 4L..10L -> TitleId.TITLE_CONTRIB_4_TO_10.titleId
        else -> null
    }

}
