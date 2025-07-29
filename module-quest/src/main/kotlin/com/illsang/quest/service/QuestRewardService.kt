package com.illsang.quest.service

import com.illsang.quest.domain.entity.QuestRewardEntity
import com.illsang.quest.domain.model.QuestRewardModel
import com.illsang.quest.dto.request.QuestRewardCreateRequest
import com.illsang.quest.dto.request.QuestRewardUpdateRequest
import com.illsang.quest.repository.QuestRewardRepository
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

        return QuestRewardModel.from(questReward)
    }

    @Transactional
    fun deleteQuestReward(id: Long) {
        val questReward = this.findById(id)

        this.questRewardRepository.delete(questReward)
    }

    private fun findById(id: Long): QuestRewardEntity = this.questRewardRepository.findByIdOrNull(id)
        ?: throw IllegalArgumentException("Quest Reward not found with id: $id")

}
