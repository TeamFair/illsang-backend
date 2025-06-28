package com.teamfair.modulequest.adapter.out.persistence

import com.teamfair.modulequest.adapter.out.persistence.repository.QuestRepository
import com.teamfair.modulequest.adapter.out.persistence.repository.QuestRewardRepository
import com.teamfair.modulequest.application.port.out.QuestRewardPersistencePort
import com.teamfair.modulequest.domain.mapper.QuestRewardMapper
import com.teamfair.modulequest.domain.model.QuestRewardModel
import org.springframework.stereotype.Component

@Component
class QuestRewardPersistenceAdapter(
    private val questRewardRepository: QuestRewardRepository,
    private val questRepository: QuestRepository
) : QuestRewardPersistencePort {

    override fun save(questRewardModel: QuestRewardModel): QuestRewardModel {
        val questEntity = questRepository.findById(questRewardModel.questId)
            .orElseThrow { IllegalArgumentException("Quest not found with id: ${questRewardModel.questId}") }
        
        val entity = QuestRewardMapper.toEntity(questRewardModel, questEntity)
        val savedEntity = questRewardRepository.save(entity)
        return QuestRewardMapper.toModel(savedEntity)
    }

    override fun findById(id: Long): QuestRewardModel? {
        return questRewardRepository.findById(id)
            .map { QuestRewardMapper.toModel(it) }
            .orElse(null)
    }

    override fun findAll(): List<QuestRewardModel> {
        return questRewardRepository.findAll()
            .map { QuestRewardMapper.toModel(it) }
    }

    override fun findByQuestId(questId: Long): List<QuestRewardModel> {
        return questRewardRepository.findByQuestId(questId)
            .map { QuestRewardMapper.toModel(it) }
    }

    override fun deleteById(id: Long) {
        questRewardRepository.deleteById(id)
    }

    override fun existsById(id: Long): Boolean {
        return questRewardRepository.existsById(id)
    }
} 