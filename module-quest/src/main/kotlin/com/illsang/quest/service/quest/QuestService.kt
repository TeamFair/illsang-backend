package com.illsang.quest.service.quest

import com.illsang.common.event.management.area.CommercialAreaExistOrThrowEvent
import com.illsang.common.event.management.banner.BannerExistOrThrowEvent
import com.illsang.common.event.management.image.ImageExistOrThrowEvent
import com.illsang.common.event.management.store.StoreExistOrThrowEvent
import com.illsang.quest.domain.entity.quest.QuestEntity
import com.illsang.quest.domain.model.quset.QuestDetailModel
import com.illsang.quest.domain.model.quset.QuestModel
import com.illsang.quest.dto.request.quest.QuestCreateRequest
import com.illsang.quest.dto.request.quest.QuestUpdateRequest
import com.illsang.quest.repository.quest.QuestRepository
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
        request.bannerId?.let {
            this.eventPublisher.publishEvent(BannerExistOrThrowEvent(it))
        }
        this.eventPublisher.publishEvent(CommercialAreaExistOrThrowEvent(request.commercialAreaCode))

        request.storeId?.let {
            this.eventPublisher.publishEvent(StoreExistOrThrowEvent(it))
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
        request.bannerId?.let {
            this.eventPublisher.publishEvent(BannerExistOrThrowEvent(it))
        }
        this.eventPublisher.publishEvent(CommercialAreaExistOrThrowEvent(request.commercialAreaCode))

        request.storeId?.let {
            this.eventPublisher.publishEvent(StoreExistOrThrowEvent(it))
        }
        quest.update(request)

        return QuestModel.from(quest)
    }

    @Transactional
    fun deleteQuest(id: Long) {
        val quest = this.findById(id)

        this.questRepository.delete(quest)
    }

    fun getQuest(id: Long): QuestDetailModel {
        val quest = this.findById(id)

        return QuestDetailModel.from(quest)
    }

    fun getAllQuest(): List<QuestModel> {
        val quests = this.questRepository.findAll()

        return quests.map { QuestModel.from(it) }
    }

    @Transactional
    fun refreshTotalPoint(questId: Long) {
        val quest = this.findById(questId)
        quest.refreshTotalPoint()
    }

    fun findById(id: Long): QuestEntity = this.questRepository.findByIdOrNull(id)
        ?: throw IllegalArgumentException("Quest not found with id: $id")

    fun existQuestImageId(id: String) {

        if (questRepository.existsByMainImageId(id)) throw IllegalArgumentException("Quest main image already exists")
        if (questRepository.existsByImageId(id)) throw IllegalArgumentException("Quest image already exists")
    }

}
