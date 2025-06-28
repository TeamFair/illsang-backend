package com.teamfair.moduleuser.application.service

import com.teamfair.moduleuser.application.command.CreateUserEmojiCommand
import com.teamfair.moduleuser.application.command.UpdateUserEmojiCommand
import com.teamfair.moduleuser.application.port.out.UserEmojiPersistencePort
import com.teamfair.moduleuser.domain.mapper.UserEmojiMapper
import com.teamfair.moduleuser.domain.model.UserEmojiModel
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class UserEmojiService(
    private val userEmojiPersistencePort: UserEmojiPersistencePort,
) {

    @Transactional
    fun createUserEmoji(command: CreateUserEmojiCommand): UserEmojiModel {
        val userEmojiModel = UserEmojiMapper.toModel(command)
        return userEmojiPersistencePort.save(userEmojiModel)
    }

    fun getUserEmojiById(id: Long): UserEmojiModel? {
        return userEmojiPersistencePort.findById(id)
    }

    fun getUserEmojisByUserId(userId: Long): List<UserEmojiModel> {
        return userEmojiPersistencePort.findByUserId(userId)
    }

    @Transactional
    fun updateUserEmoji(command: UpdateUserEmojiCommand): UserEmojiModel? {
        val existingUserEmoji = userEmojiPersistencePort.findById(command.id) ?: return null
        val updatedUserEmoji = UserEmojiMapper.toModel(command, existingUserEmoji)
        return userEmojiPersistencePort.save(updatedUserEmoji)
    }

    @Transactional
    fun deleteUserEmoji(id: Long): Boolean {
        return if (userEmojiPersistencePort.existsById(id)) {
            userEmojiPersistencePort.deleteById(id)
            true
        } else {
            false
        }
    }
} 