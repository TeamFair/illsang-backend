package com.illsang.moduleuser.application.service

import com.illsang.moduleuser.application.command.CreateUserXpHistoryCommand
import com.illsang.moduleuser.application.command.UpdateUserXpHistoryCommand
import com.illsang.moduleuser.application.port.out.UserXpHistoryPersistencePort
import com.illsang.moduleuser.domain.mapper.UserXpHistoryMapper
import com.illsang.moduleuser.domain.model.UserXpHistoryModel
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class UserXpHistoryService(
    private val userXpHistoryPersistencePort: UserXpHistoryPersistencePort
) {

    @Transactional
    fun createUserXpHistory(command: CreateUserXpHistoryCommand): UserXpHistoryModel {
        val userXpHistoryModel = UserXpHistoryMapper.toModel(command)
        return userXpHistoryPersistencePort.save(userXpHistoryModel)
    }

    fun getUserXpHistoryById(id: Long): UserXpHistoryModel? {
        return userXpHistoryPersistencePort.findById(id)
    }

    fun getUserXpHistoriesByUserId(userId: Long): List<UserXpHistoryModel> {
        return userXpHistoryPersistencePort.findByUserId(userId)
    }

    fun getUserXpHistoriesByUserIdAndXpType(userId: Long, xpType: com.illsang.moduleuser.domain.model.XpType): List<UserXpHistoryModel> {
        return userXpHistoryPersistencePort.findByUserIdAndXpType(userId, xpType)
    }

    @Transactional
    fun updateUserXpHistory(command: UpdateUserXpHistoryCommand): UserXpHistoryModel? {
        val existingUserXpHistory = userXpHistoryPersistencePort.findById(command.id) ?: return null
        val updatedUserXpHistory = UserXpHistoryMapper.toModel(command, existingUserXpHistory)
        return userXpHistoryPersistencePort.save(updatedUserXpHistory)
    }

    @Transactional
    fun deleteUserXpHistory(id: Long): Boolean {
        return if (userXpHistoryPersistencePort.existsById(id)) {
            userXpHistoryPersistencePort.deleteById(id)
            true
        } else {
            false
        }
    }
}
