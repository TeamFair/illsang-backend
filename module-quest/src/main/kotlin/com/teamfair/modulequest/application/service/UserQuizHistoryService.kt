package com.teamfair.modulequest.application.service

import com.teamfair.modulequest.application.command.CreateUserQuizHistoryCommand
import com.teamfair.modulequest.application.command.UpdateUserQuizHistoryCommand
import com.teamfair.modulequest.application.port.out.UserQuizHistoryPersistencePort
import com.teamfair.modulequest.domain.mapper.UserQuizHistoryMapper
import com.teamfair.modulequest.domain.model.UserQuizHistoryModel
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class UserQuizHistoryService(
    private val userQuizHistoryPersistencePort: UserQuizHistoryPersistencePort
) {

    @Transactional
    fun createUserQuizHistory(command: CreateUserQuizHistoryCommand): UserQuizHistoryModel {
        val userQuizHistoryModel = UserQuizHistoryMapper.toModel(command)
        return userQuizHistoryPersistencePort.save(userQuizHistoryModel)
    }

    fun getUserQuizHistoryById(id: Long): UserQuizHistoryModel? {
        return userQuizHistoryPersistencePort.findById(id)
    }

    fun getAllUserQuizHistories(): List<UserQuizHistoryModel> {
        return userQuizHistoryPersistencePort.findAll()
    }

    fun getUserQuizHistoriesByUserId(userId: Long): List<UserQuizHistoryModel> {
        return userQuizHistoryPersistencePort.findByUserId(userId)
    }

    fun getUserQuizHistoriesByQuizId(quizId: Long): List<UserQuizHistoryModel> {
        return userQuizHistoryPersistencePort.findByQuizId(quizId)
    }

    fun getUserQuizHistoriesByUserMissionHistoryId(userMissionHistoryId: Long): List<UserQuizHistoryModel> {
        return userQuizHistoryPersistencePort.findByUserMissionHistoryId(userMissionHistoryId)
    }

    @Transactional
    fun updateUserQuizHistory(command: UpdateUserQuizHistoryCommand): UserQuizHistoryModel? {
        val existingUserQuizHistory = userQuizHistoryPersistencePort.findById(command.id) ?: return null
        val updatedUserQuizHistory = UserQuizHistoryMapper.toModel(command, existingUserQuizHistory)
        return userQuizHistoryPersistencePort.save(updatedUserQuizHistory)
    }

    @Transactional
    fun deleteUserQuizHistory(id: Long): Boolean {
        return if (userQuizHistoryPersistencePort.existsById(id)) {
            userQuizHistoryPersistencePort.deleteById(id)
            true
        } else {
            false
        }
    }
} 