package com.teamfair.moduleuser.application.service

import com.teamfair.moduleuser.application.command.CreateUserCommand
import com.teamfair.moduleuser.application.command.UpdateUserCommand
import com.teamfair.moduleuser.application.port.out.UserPersistencePort
import com.teamfair.moduleuser.domain.model.UserModel
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional
class UserService(
    private val userPersistencePort: UserPersistencePort
) {
    
    fun createUser(command: CreateUserCommand): UserModel {
        when {
            userPersistencePort.existsByEmail(command.email) -> {
                throw IllegalArgumentException("User with email ${command.email} already exists")
            }
            else -> {
                val userModel = UserModel(
                    email = command.email,
                    channel = command.channel,
                    nickname = command.nickname,
                    status = command.status,
                    statusUpdatedAt = LocalDateTime.now(),
                    profileImageId = command.profileImageId
                )
                userModel.validate()
                return userPersistencePort.save(userModel)
            }
        }
    }
    
    @Transactional(readOnly = true)
    fun getUser(id: Long): UserModel {
        return userPersistencePort.findById(id) 
            ?: throw IllegalArgumentException("User not found with id: $id")
    }
    
    @Transactional(readOnly = true)
    fun getUserByEmail(email: String): UserModel {
        return userPersistencePort.findByEmail(email) 
            ?: throw IllegalArgumentException("User not found with email: $email")
    }
    
    @Transactional(readOnly = true)
    fun getAllUsers(): List<UserModel> {
        return userPersistencePort.findAll()
    }
    
    fun updateUser(command: UpdateUserCommand): UserModel {
        val existingUser = userPersistencePort.findById(command.id)
            ?: throw IllegalArgumentException("User not found with id: ${command.id}")
        
        // 이메일 변경 시 중복 체크
        when {
            command.email != null && command.email != existingUser.email -> {
                when {
                    userPersistencePort.existsByEmail(command.email) -> {
                        throw IllegalArgumentException("User with email ${command.email} already exists")
                    }
                }
            }
        }
        
        val updatedUser = existingUser.copy(
            email = command.email ?: existingUser.email,
            channel = command.channel ?: existingUser.channel,
            nickname = command.nickname ?: existingUser.nickname,
            status = command.status ?: existingUser.status,
            statusUpdatedAt = if (command.status != null && command.status != existingUser.status) {
                LocalDateTime.now()
            } else {
                existingUser.statusUpdatedAt
            },
            profileImageId = command.profileImageId ?: existingUser.profileImageId
        )
        updatedUser.validate()
        return userPersistencePort.save(updatedUser)
    }
    
    fun deleteUser(id: Long) {
        when {
            !userPersistencePort.existsById(id) -> {
                throw IllegalArgumentException("User not found with id: $id")
            }
            else -> userPersistencePort.deleteById(id)
        }
    }
} 