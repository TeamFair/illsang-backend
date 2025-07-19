package com.illsang.user.service

import com.illsang.user.application.command.CreateUserXpHistoryCommand
import com.illsang.user.application.command.UpdateUserXpHistoryCommand
import com.illsang.user.application.port.out.UserXpHistoryPersistencePort
import com.illsang.user.domain.mapper.UserXpHistoryMapper
import com.illsang.user.domain.model.UserXpHistoryModel
import com.illsang.user.enums.XpType
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

    fun getUserXpHistoriesByUserIdAndXpType(userId: Long, xpType: XpType): List<UserXpHistoryModel> {
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
