package com.teamfair.moduleuser.application.service

import com.teamfair.moduleuser.application.command.CreateUserXpCommand
import com.teamfair.moduleuser.application.command.UpdateUserXpCommand
import com.teamfair.moduleuser.application.port.out.UserXpPersistencePort
import com.teamfair.moduleuser.domain.mapper.UserXpMapper
import com.teamfair.moduleuser.domain.model.UserXpModel
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class UserXpService(
    private val userXpPersistencePort: UserXpPersistencePort
) {

    /**
     * create user xp
     */
    @Transactional
    fun createUserXp(command: CreateUserXpCommand): UserXpModel {
        val userXpModel = UserXpMapper.toModel(command)
        return userXpPersistencePort.save(userXpModel)
    }

    /**
     * find user xp by id
     */
    fun getUserXpById(id: Long): UserXpModel? {
        return userXpPersistencePort.findById(id)
    }

    /**
     * find all user xp
     */
    fun getUserXpsByUserId(userId: Long): List<UserXpModel> {
        return userXpPersistencePort.findByUserId(userId)
    }

    /**
     * find user xp by user id and xp type
     */
    fun getUserXpByUserIdAndXpType(userId: Long, xpType: com.teamfair.moduleuser.domain.model.XpType): UserXpModel? {
        return userXpPersistencePort.findByUserIdAndXpType(userId, xpType)
    }

    /**
     * update user xp
     */
    @Transactional
    fun updateUserXp(command: UpdateUserXpCommand): UserXpModel? {
        val existingUserXp = userXpPersistencePort.findById(command.id) ?: return null
        val updatedUserXp = UserXpMapper.toModel(command, existingUserXp)
        return userXpPersistencePort.save(updatedUserXp)
    }

    /**
     * delete user xp
     *
     * @param id user xp id
     * @return response entity
     */
    @Transactional
    fun deleteUserXp(id: Long): Boolean {
        return if (userXpPersistencePort.existsById(id)) {
            userXpPersistencePort.deleteById(id)
            true
        } else {
            false
        }
    }

    /**
     * add point to user xp
     */
    @Transactional
    fun addPoint(userId: Long, xpType: com.teamfair.moduleuser.domain.model.XpType, additionalPoint: Int): UserXpModel? {
        val existingUserXp = userXpPersistencePort.findByUserIdAndXpType(userId, xpType)
        return if (existingUserXp != null) {
            val updatedUserXp = existingUserXp.copy(point = existingUserXp.point + additionalPoint)
            userXpPersistencePort.save(updatedUserXp)
        } else {
            null
        }
    }
} 