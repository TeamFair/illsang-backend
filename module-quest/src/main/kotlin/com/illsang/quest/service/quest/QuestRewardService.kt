package com.illsang.quest.service.quest

import com.illsang.quest.domain.entity.quest.QuestRewardEntity
import com.illsang.quest.domain.model.quset.QuestRewardModel
import com.illsang.quest.dto.request.quest.QuestRewardCreateRequest
import com.illsang.quest.dto.request.quest.QuestRewardUpdateRequest
import com.illsang.quest.repository.quest.QuestRewardRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class QuestRewardService(
    private val questRewardRepository: QuestRewardRepository,
    private val questService: QuestService,
) {

    @Transactional
    fun createQuestReward(request: QuestRewardCreateRequest): QuestRewardModel {
        val quest = this.questService.findById(request.questId)

        val questReward = request.toEntity(quest)
        this.questRewardRepository.save(questReward)
        this.questService.refreshTotalPoint(quest.id!!)

        return QuestRewardModel.from(questReward)
    }

    fun getQuestRewardById(id: Long): QuestRewardModel {
        val questReward = this.findById(id)

        return QuestRewardModel.from(questReward)
    }

    @Transactional
    fun updateQuestReward(id: Long, request: QuestRewardUpdateRequest): QuestRewardModel {
        val questReward = this.findById(id)

        questReward.update(request)
        this.questService.refreshTotalPoint(questReward.quest.id!!)

        return QuestRewardModel.from(questReward)
    }

    @Transactional
    fun deleteQuestReward(id: Long) {
        val questReward = this.findById(id)

        this.questRewardRepository.delete(questReward)
        this.questRewardRepository.flush()
        this.questService.refreshTotalPoint(questReward.quest.id!!)
    }

    private fun findById(id: Long): QuestRewardEntity = this.questRewardRepository.findByIdOrNull(id)
        ?: throw IllegalArgumentException("Quest Reward not found with id: $id")

}
