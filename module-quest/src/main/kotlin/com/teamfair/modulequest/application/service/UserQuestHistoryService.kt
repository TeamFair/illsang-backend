package com.teamfair.modulequest.application.service

import com.teamfair.modulequest.application.command.CreateUserQuestHistoryCommand
import com.teamfair.modulequest.application.command.UpdateUserQuestHistoryCommand
import com.teamfair.modulequest.application.port.out.UserQuestHistoryPersistencePort
import com.teamfair.modulequest.domain.mapper.UserQuestHistoryMapper
import com.teamfair.modulequest.domain.model.UserQuestHistoryModel
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class UserQuestHistoryService(
    private val userQuestHistoryPersistencePort: UserQuestHistoryPersistencePort
) {

    @Transactional
    fun createUserQuestHistory(command: CreateUserQuestHistoryCommand): UserQuestHistoryModel {
        val userQuestHistoryModel = UserQuestHistoryMapper.toModel(command)
        return userQuestHistoryPersistencePort.save(userQuestHistoryModel)
    }

    fun getUserQuestHistoryById(id: Long): UserQuestHistoryModel? {
        return userQuestHistoryPersistencePort.findById(id)
    }

    fun getAllUserQuestHistories(): List<UserQuestHistoryModel> {
        return userQuestHistoryPersistencePort.findAll()
    }

    fun getUserQuestHistoriesByUserId(userId: Long): List<UserQuestHistoryModel> {
        return userQuestHistoryPersistencePort.findByUserId(userId)
    }

    fun getUserQuestHistoriesByQuestId(questId: Long): List<UserQuestHistoryModel> {
        return userQuestHistoryPersistencePort.findByQuestId(questId)
    }

    @Transactional
    fun updateUserQuestHistory(command: UpdateUserQuestHistoryCommand): UserQuestHistoryModel? {
        val existingUserQuestHistory = userQuestHistoryPersistencePort.findById(command.id) ?: return null
        val updatedUserQuestHistory = UserQuestHistoryMapper.toModel(command, existingUserQuestHistory)
        return userQuestHistoryPersistencePort.save(updatedUserQuestHistory)
    }

    @Transactional
    fun deleteUserQuestHistory(id: Long): Boolean {
        return if (userQuestHistoryPersistencePort.existsById(id)) {
            userQuestHistoryPersistencePort.deleteById(id)
            true
        } else {
            false
        }
    }
} 