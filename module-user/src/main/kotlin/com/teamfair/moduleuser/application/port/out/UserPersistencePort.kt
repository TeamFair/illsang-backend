package com.teamfair.moduleuser.application.port.out

import com.teamfair.moduleuser.domain.model.UserModel

interface UserPersistencePort {
    fun save(userModel: UserModel): UserModel
    fun findById(id: Long): UserModel?
    fun findByEmail(email: String): UserModel?
    fun findAll(): List<UserModel>
    fun deleteById(id: Long)
    fun existsById(id: Long): Boolean
    fun existsByEmail(email: String): Boolean
} 