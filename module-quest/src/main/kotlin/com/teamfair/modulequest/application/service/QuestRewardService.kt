package com.teamfair.modulequest.application.service

import com.teamfair.modulequest.application.command.CreateQuestRewardCommand
import com.teamfair.modulequest.application.command.UpdateQuestRewardCommand
import com.teamfair.modulequest.application.port.out.QuestRewardPersistencePort
import com.teamfair.modulequest.domain.mapper.QuestRewardMapper
import com.teamfair.modulequest.domain.model.QuestRewardModel
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class QuestRewardService(
    private val questRewardPersistencePort: QuestRewardPersistencePort
) {

    @Transactional
    fun createQuestReward(command: CreateQuestRewardCommand): QuestRewardModel {
        val questRewardModel = QuestRewardMapper.toModel(command)
        return questRewardPersistencePort.save(questRewardModel)
    }

    fun getQuestRewardById(id: Long): QuestRewardModel? {
        return questRewardPersistencePort.findById(id)
    }

    fun getAllQuestRewards(): List<QuestRewardModel> {
        return questRewardPersistencePort.findAll()
    }

    fun getQuestRewardsByQuestId(questId: Long): List<QuestRewardModel> {
        return questRewardPersistencePort.findByQuestId(questId)
    }

    @Transactional
    fun updateQuestReward(command: UpdateQuestRewardCommand): QuestRewardModel? {
        val existingQuestReward = questRewardPersistencePort.findById(command.id) ?: return null
        val updatedQuestReward = QuestRewardMapper.toModel(command, existingQuestReward)
        return questRewardPersistencePort.save(updatedQuestReward)
    }

    @Transactional
    fun deleteQuestReward(id: Long): Boolean {
        return if (questRewardPersistencePort.existsById(id)) {
            questRewardPersistencePort.deleteById(id)
            true
        } else {
            false
        }
    }
} 