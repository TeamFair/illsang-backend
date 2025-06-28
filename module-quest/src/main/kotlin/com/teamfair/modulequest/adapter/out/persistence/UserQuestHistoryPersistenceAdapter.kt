package com.teamfair.modulequest.adapter.out.persistence

import com.teamfair.modulequest.adapter.out.persistence.repository.QuestRepository
import com.teamfair.modulequest.adapter.out.persistence.repository.UserQuestHistoryRepository
import com.teamfair.modulequest.application.port.out.UserQuestHistoryPersistencePort
import com.teamfair.modulequest.domain.mapper.UserQuestHistoryMapper
import com.teamfair.modulequest.domain.model.UserQuestHistoryModel
import org.springframework.stereotype.Component

@Component
class UserQuestHistoryPersistenceAdapter(
    private val userQuestHistoryRepository: UserQuestHistoryRepository,
    private val questRepository: QuestRepository
) : UserQuestHistoryPersistencePort {

    override fun save(userQuestHistoryModel: UserQuestHistoryModel): UserQuestHistoryModel {
        val questEntity = questRepository.findById(userQuestHistoryModel.questId)
            .orElseThrow { IllegalArgumentException("Quest not found with id: ${userQuestHistoryModel.questId}") }
        
        val entity = UserQuestHistoryMapper.toEntity(userQuestHistoryModel, questEntity)
        val savedEntity = userQuestHistoryRepository.save(entity)
        return UserQuestHistoryMapper.toModel(savedEntity)
    }

    override fun findById(id: Long): UserQuestHistoryModel? {
        return userQuestHistoryRepository.findById(id)
            .map { UserQuestHistoryMapper.toModel(it) }
            .orElse(null)
    }

    override fun findAll(): List<UserQuestHistoryModel> {
        return userQuestHistoryRepository.findAll()
            .map { UserQuestHistoryMapper.toModel(it) }
    }

    override fun findByUserId(userId: Long): List<UserQuestHistoryModel> {
        return userQuestHistoryRepository.findByUserId(userId)
            .map { UserQuestHistoryMapper.toModel(it) }
    }

    override fun findByQuestId(questId: Long): List<UserQuestHistoryModel> {
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