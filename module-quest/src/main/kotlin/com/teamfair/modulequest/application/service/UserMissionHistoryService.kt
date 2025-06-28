package com.teamfair.modulequest.application.service

import com.teamfair.modulequest.application.command.CreateUserMissionHistoryCommand
import com.teamfair.modulequest.application.command.UpdateUserMissionHistoryCommand
import com.teamfair.modulequest.application.port.out.UserMissionHistoryPersistencePort
import com.teamfair.modulequest.domain.mapper.UserMissionHistoryMapper
import com.teamfair.modulequest.domain.model.UserMissionHistory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class UserMissionHistoryService(
    private val userMissionHistoryPersistencePort: UserMissionHistoryPersistencePort
) {

    @Transactional
    fun createUserMissionHistory(command: CreateUserMissionHistoryCommand): UserMissionHistory {
        val userMissionHistoryModel = UserMissionHistoryMapper.toModel(command)
        return userMissionHistoryPersistencePort.save(userMissionHistoryModel)
    }

    fun getUserMissionHistoryById(id: Long): UserMissionHistory? {
        return userMissionHistoryPersistencePort.findById(id)
    }

    fun getAllUserMissionHistories(): List<UserMissionHistory> {
        return userMissionHistoryPersistencePort.findAll()
    }

    fun getUserMissionHistoriesByUserId(userId: Long): List<UserMissionHistory> {
        return userMissionHistoryPersistencePort.findByUserId(userId)
    }

    fun getUserMissionHistoriesByMissionId(missionId: Long): List<UserMissionHistory> {
        return userMissionHistoryPersistencePort.findByMissionId(missionId)
    }

    @Transactional
    fun updateUserMissionHistory(command: UpdateUserMissionHistoryCommand): UserMissionHistory? {
        val existingUserMissionHistory = userMissionHistoryPersistencePort.findById(command.id) ?: return null
        val updatedUserMissionHistory = UserMissionHistoryMapper.toModel(command, existingUserMissionHistory)
        return userMissionHistoryPersistencePort.save(updatedUserMissionHistory)
    }

    @Transactional
    fun deleteUserMissionHistory(id: Long): Boolean {
        return if (userMissionHistoryPersistencePort.existsById(id)) {
            userMissionHistoryPersistencePort.deleteById(id)
            true
        } else {
            false
        }
    }
} 