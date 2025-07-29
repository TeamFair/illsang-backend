package com.illsang.quest.service

import com.illsang.common.event.management.image.ImageExistOrThrowEvent
import com.illsang.quest.domain.entity.QuestEntity
import com.illsang.quest.domain.model.QuestModel
import com.illsang.quest.dto.request.QuestCreateRequest
import com.illsang.quest.dto.request.QuestUpdateRequest
import com.illsang.quest.repository.QuestRepository
import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class QuestService(
    private val eventPublisher: ApplicationEventPublisher,
    private val questRepository: QuestRepository,
) {

    @Transactional
    fun createQuest(request: QuestCreateRequest): QuestModel {
        request.imageId?.let {
            this.eventPublisher.publishEvent(ImageExistOrThrowEvent(it))
        }
        request.mainImageId?.let {
            this.eventPublisher.publishEvent(ImageExistOrThrowEvent(it))
        }

        val quest = request.toEntity()
        this.questRepository.save(quest)

        return QuestModel.from(quest)
    }

    @Transactional
    fun updateQuest(id: Long, request: QuestUpdateRequest): QuestModel {
        val quest = this.findById(id)

        request.imageId?.let {
            this.eventPublisher.publishEvent(ImageExistOrThrowEvent(it))
        }
        request.mainImageId?.let {
            this.eventPublisher.publishEvent(ImageExistOrThrowEvent(it))
        }

        quest.update(request)

        return QuestModel.from(quest)
    }

    @Transactional
    fun deleteQuest(id: Long) {
        val quest = this.findById(id)

        this.questRepository.delete(quest)
    }

    fun getQuest(id: Long): QuestModel {
        val quest = this.findById(id)

        return QuestModel.from(quest)
    }

    fun findById(id: Long): QuestEntity = this.questRepository.findByIdOrNull(id)
        ?: throw IllegalArgumentException("Quest not found with id: $id")

}
