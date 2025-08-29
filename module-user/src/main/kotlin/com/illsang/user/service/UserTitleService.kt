package com.illsang.user.service

import com.illsang.common.enums.TitleId
import com.illsang.common.event.user.title.GetTitleInfoEvent
import com.illsang.user.domain.entity.UserTitleEntity
import com.illsang.user.domain.model.UserTitleModel
import com.illsang.user.repository.UserRepository
import com.illsang.user.repository.UserTitleRepository
import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class UserTitleService(
    private val userTitleRepository: UserTitleRepository,
    private val eventPublisher: ApplicationEventPublisher,
    private val userService: UserService,
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

    fun updateUserTitle(userTitleId: Long, userId: String) {
        val userTitle = this.findByUserIdAndId(userTitleId, userId)
        val user = userService.findById(userId)

        user.updateTitle(userTitle)
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

}
