package com.illsang.modulequest.application.service

import com.illsang.modulequest.application.command.CreateUserMissionHistoryCommand
import com.illsang.modulequest.application.command.UpdateUserMissionHistoryCommand
import com.illsang.modulequest.application.port.out.UserMissionHistoryPersistencePort
import com.illsang.modulequest.domain.mapper.UserMissionHistoryMapper
import com.illsang.modulequest.domain.model.UserMissionHistoryModel
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class UserMissionHistoryService(
    private val userMissionHistoryPersistencePort: UserMissionHistoryPersistencePort
) {

    @Transactional
    fun createUserMissionHistory(command: CreateUserMissionHistoryCommand): UserMissionHistoryModel {
        val userMissionHistoryModel = UserMissionHistoryMapper.toModel(command)
        return userMissionHistoryPersistencePort.save(userMissionHistoryModel)
    }

    fun getUserMissionHistoryById(id: Long): UserMissionHistoryModel? {
        return userMissionHistoryPersistencePort.findById(id)
    }

    fun getAllUserMissionHistories(): List<UserMissionHistoryModel> {
        return userMissionHistoryPersistencePort.findAll()
    }

    fun getUserMissionHistoriesByUserId(userId: Long): List<UserMissionHistoryModel> {
        return userMissionHistoryPersistencePort.findByUserId(userId)
    }

    fun getUserMissionHistoriesByMissionId(missionId: Long): List<UserMissionHistoryModel> {
        return userMissionHistoryPersistencePort.findByMissionId(missionId)
    }

    @Transactional
    fun updateUserMissionHistory(command: UpdateUserMissionHistoryCommand): UserMissionHistoryModel? {
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
