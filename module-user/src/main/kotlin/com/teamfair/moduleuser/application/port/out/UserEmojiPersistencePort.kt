package com.teamfair.moduleuser.application.port.out

import com.teamfair.moduleuser.domain.model.UserEmojiModel

interface UserEmojiPersistencePort {
    fun save(userEmoji: UserEmojiModel): UserEmojiModel
    fun findById(id: Long): UserEmojiModel?
    fun findByUserId(userId: Long): List<UserEmojiModel>
    fun deleteById(id: Long)
    fun existsById(id: Long): Boolean
} 