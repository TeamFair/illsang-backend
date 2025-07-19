package com.illsang.user.service

import com.illsang.user.application.command.CreateUserEmojiCommand
import com.illsang.user.application.command.UpdateUserEmojiCommand
import com.illsang.user.application.port.out.UserEmojiPersistencePort
import com.illsang.user.domain.mapper.UserEmojiMapper
import com.illsang.user.domain.model.UserEmojiModel
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class UserEmojiService(
    private val userEmojiPersistencePort: UserEmojiPersistencePort,
) {

    /**
     * create user emoji
     */
    @Transactional
    fun createUserEmoji(command: CreateUserEmojiCommand): UserEmojiModel {
        val userEmojiModel = UserEmojiMapper.toModel(command)
        return userEmojiPersistencePort.save(userEmojiModel)
    }

    /**
     * find user emoji by id
     */
    fun getUserEmojiById(id: Long): UserEmojiModel? {
        return userEmojiPersistencePort.findById(id)
    }

    /**
     * find user emoji by user id
     */
    fun getUserEmojisByUserId(userId: Long): List<UserEmojiModel> {
        return userEmojiPersistencePort.findByUserId(userId)
    }

    /**
     * update user emoji
     */
    @Transactional
    fun updateUserEmoji(command: UpdateUserEmojiCommand): UserEmojiModel? {
        val existingUserEmoji = userEmojiPersistencePort.findById(command.id) ?: return null
        val updatedUserEmoji = UserEmojiMapper.toModel(command, existingUserEmoji)
        return userEmojiPersistencePort.save(updatedUserEmoji)
    }

    /**
     * delete user emoji
     */
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
