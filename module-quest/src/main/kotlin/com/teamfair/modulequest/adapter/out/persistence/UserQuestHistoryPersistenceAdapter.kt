package com.teamfair.modulequest.adapter.out.persistence

import com.teamfair.modulequest.adapter.out.persistence.entity.QuestEntity
import com.teamfair.modulequest.adapter.out.persistence.entity.UserQuestHistoryEntity
import com.teamfair.modulequest.adapter.out.persistence.repository.QuestRepository
import com.teamfair.modulequest.adapter.out.persistence.repository.UserQuestHistoryRepository
import com.teamfair.modulequest.application.port.out.UserQuestHistoryPersistencePort
import com.teamfair.modulequest.domain.mapper.UserQuestHistoryMapper
import com.teamfair.modulequest.domain.model.UserQuestHistory
import org.springframework.stereotype.Component

@Component
class UserQuestHistoryPersistenceAdapter(
    private val userQuestHistoryRepository: UserQuestHistoryRepository,
    private val questRepository: QuestRepository
) : UserQuestHistoryPersistencePort {

    override fun save(userQuestHistory: UserQuestHistory): UserQuestHistory {
        val questEntity = questRepository.findById(userQuestHistory.questId)
            .orElseThrow { IllegalArgumentException("Quest not found with id: ${userQuestHistory.questId}") }
        
        val entity = UserQuestHistoryMapper.toEntity(userQuestHistory, questEntity)
        val savedEntity = userQuestHistoryRepository.save(entity)
        return UserQuestHistoryMapper.toModel(savedEntity)
    }

    override fun findById(id: Long): UserQuestHistory? {
        return userQuestHistoryRepository.findById(id)
            .map { UserQuestHistoryMapper.toModel(it) }
            .orElse(null)
    }

    override fun findAll(): List<UserQuestHistory> {
        return userQuestHistoryRepository.findAll()
            .map { UserQuestHistoryMapper.toModel(it) }
    }

    override fun findByUserId(userId: Long): List<UserQuestHistory> {
        return userQuestHistoryRepository.findByUserId(userId)
            .map { UserQuestHistoryMapper.toModel(it) }
    }

    override fun findByQuestId(questId: Long): List<UserQuestHistory> {
        return userQuestHistoryRepository.findByQuestId(questId)
            .map { UserQuestHistoryMapper.toModel(it) }
    }

    override fun deleteById(id: Long) {
        userQuestHistoryRepository.deleteById(id)
    }

    override fun existsById(id: Long): Boolean {
        return userQuestHistoryRepository.existsById(id)
    }
} 