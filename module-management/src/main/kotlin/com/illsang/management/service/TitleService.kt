package com.illsang.management.service

import com.illsang.common.enums.TitleId
import com.illsang.common.event.quest.UserTitleQuestCompleteEvent
import com.illsang.common.event.quest.UserTitleUserCreateEvent
import com.illsang.management.domain.entity.TitleEntity
import com.illsang.management.domain.model.TitleModel
import com.illsang.management.dto.request.TitleRequest
import com.illsang.management.repository.TitleRepository
import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class TitleService(
    private val titleRepository: TitleRepository,
    private val eventPublisher: ApplicationEventPublisher,
) {

    fun getTitle(id: String): TitleModel {
        val title = this.findById(id)
        return TitleModel.from(title)
    }

    fun getAllTitles(): List<TitleModel> {
        return titleRepository.findAll()
            .map { TitleModel.from(it) }
    }

    @Transactional
    fun createTitle(request: TitleRequest): TitleModel {
        val title = request.toEntity()
        return TitleModel.from(titleRepository.save(title))
    }

    @Transactional
    fun updateTitle(id: String, request: TitleRequest): TitleModel {
        val title = this.findById(id)
        title.update(request)
        return TitleModel.from(title)
    }

    @Transactional
    fun deleteTitle(id: String) {
        titleRepository.deleteById(id)
    }

    private fun findById(id: String): TitleEntity =
        titleRepository.findByIdOrNull(id)
            ?: throw IllegalArgumentException("Title not found with id: $id")

    fun getTitleForQuestComplete(userId: String, maxStreak: Int) {
        val id = when {
            maxStreak >= 360 -> TitleId.TITLE_360_DAYS.titleId
            maxStreak >= 240 -> TitleId.TITLE_TWO_FORTY_DAYS.titleId
            maxStreak >= 120 -> TitleId.TITLE_ONE_TWENTY_DAYS.titleId
            maxStreak >= 60  -> TitleId.TITLE_SIXTY_DAYS.titleId
            maxStreak >= 30  -> TitleId.TITLE_THIRTY_DAYS.titleId
            maxStreak >= 14  -> TitleId.TITLE_FOURTEEN_DAYS.titleId
            maxStreak >= 7   -> TitleId.TITLE_SEVEN_DAYS.titleId
            maxStreak >= 4   -> TitleId.TITLE_FOUR_DAYS.titleId
            maxStreak >= 2   -> TitleId.TITLE_ONE_DAY.titleId
            maxStreak >= 1   -> TitleId.TITLE_FIRST_STEP.titleId
            else -> null
        }

        if(id != null){
            val title = this.findById(id)
            eventPublisher.publishEvent(
                UserTitleQuestCompleteEvent(
                    userId = userId,
                    titleId = title.id,
                    titleName = title.name,
                    titleGrade = title.grade,
                    titleType = title.type
                )
            )
        }
    }

    fun getTitleForUserCreate(userId: String){
        val title = this.findById(TitleId.TITLE_DAILY_PIONEER.titleId)
        eventPublisher.publishEvent(
            UserTitleUserCreateEvent(
                userId = userId,
                titleId = title.id,
                titleName = title.name,
                titleGrade = title.grade,
                titleType = title.type
            )
        )
    }
}