package com.teamfair.modulequest.adapter.out.persistence

import com.teamfair.modulequest.adapter.out.persistence.entity.QuestEntity
import com.teamfair.modulequest.adapter.out.persistence.entity.QuestRewardEntity
import com.teamfair.modulequest.adapter.out.persistence.repository.QuestRepository
import com.teamfair.modulequest.adapter.out.persistence.repository.QuestRewardRepository
import com.teamfair.modulequest.application.port.out.QuestRewardPersistencePort
import com.teamfair.modulequest.domain.mapper.QuestRewardMapper
import com.teamfair.modulequest.domain.model.QuestReward
import org.springframework.stereotype.Component

@Component
class QuestRewardPersistenceAdapter(
    private val questRewardRepository: QuestRewardRepository,
    private val questRepository: QuestRepository
) : QuestRewardPersistencePort {

    override fun save(questReward: QuestReward): QuestReward {
        val questEntity = questRepository.findById(questReward.questId)
            .orElseThrow { IllegalArgumentException("Quest not found with id: ${questReward.questId}") }
        
        val entity = QuestRewardMapper.toEntity(questReward, questEntity)
        val savedEntity = questRewardRepository.save(entity)
        return QuestRewardMapper.toModel(savedEntity)
    }

    override fun findById(id: Long): QuestReward? {
        return questRewardRepository.findById(id)
            .map { QuestRewardMapper.toModel(it) }
            .orElse(null)
    }

    override fun findAll(): List<QuestReward> {
        return questRewardRepository.findAll()
            .map { QuestRewardMapper.toModel(it) }
    }

    override fun findByQuestId(questId: Long): List<QuestReward> {
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