package com.teamfair.moduleuser.adapter.out.persistence

import com.teamfair.moduleuser.adapter.out.persistence.repository.UserRepository
import com.teamfair.moduleuser.application.port.out.UserPersistencePort
import com.teamfair.moduleuser.domain.mapper.UserMapper
import com.teamfair.moduleuser.domain.model.UserModel
import org.springframework.stereotype.Component

@Component
class UserPersistenceAdapter(
    private val userRepository: UserRepository
) : UserPersistencePort {

    /**
     * @throws IllegalArgumentException if email already exists
     */
    override fun save(userModel: UserModel): UserModel {
        val entity = UserMapper.toEntity(userModel)
        val savedEntity = userRepository.save(entity)
        return UserMapper.toModel(savedEntity)
    }

    /**
     * @throws IllegalArgumentException if user not found
     */
    override fun findById(id: Long): UserModel? {
        return userRepository.findById(id)
            .map { UserMapper.toModel(it) }
            .orElse(null)
    }

    /**
     * @throws IllegalArgumentException if user not found
     */
    override fun findByEmail(email: String): UserModel? {
        return userRepository.findByEmail(email)
            ?.let { UserMapper.toModel(it) }
    }

    /**
     * @throws IllegalArgumentException if user not found
     */
    override fun findAll(): List<UserModel> {
        return userRepository.findAll()
            .map { UserMapper.toModel(it) }
    }

    /**
     * @throws IllegalArgumentException if user not found
     */
    override fun deleteById(id: Long) {
        userRepository.deleteById(id)
    }

    /**
     * @throws IllegalArgumentException if user not found
     */
    override fun existsById(id: Long): Boolean {
        return userRepository.existsById(id)
    }

    /**
     * @throws IllegalArgumentException if email already exists
     */
    override fun existsByEmail(email: String): Boolean {
        return userRepository.findByEmail(email) != null
    }
} 