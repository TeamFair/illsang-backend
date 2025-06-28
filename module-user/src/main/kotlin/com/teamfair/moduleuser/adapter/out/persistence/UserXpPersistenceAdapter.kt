package com.teamfair.moduleuser.adapter.out.persistence

import com.teamfair.moduleuser.adapter.out.persistence.repository.UserRepository
import com.teamfair.moduleuser.adapter.out.persistence.repository.UserXpRepository
import com.teamfair.moduleuser.application.port.out.UserXpPersistencePort
import com.teamfair.moduleuser.domain.mapper.UserXpMapper
import com.teamfair.moduleuser.domain.model.UserXpModel
import org.springframework.stereotype.Component

@Component
class UserXpPersistenceAdapter(
    private val userXpRepository: UserXpRepository,
    private val userRepository: UserRepository
) : UserXpPersistencePort {

    override fun save(userXp: UserXpModel): UserXpModel {
        val userEntity = userRepository.findById(userXp.userId)
            .orElseThrow { IllegalArgumentException("User not found with id: ${userXp.userId}") }
        
        val entity = UserXpMapper.toEntity(userXp, userEntity)
        val savedEntity = userXpRepository.save(entity)
        return UserXpMapper.toModel(savedEntity)
    }

    override fun findById(id: Long): UserXpModel? {
        return userXpRepository.findById(id)
            .map { UserXpMapper.toModel(it) }
            .orElse(null)
    }

    override fun findByUserId(userId: Long): List<UserXpModel> {
        return userXpRepository.findByUserEntityId(userId)
            .map { UserXpMapper.toModel(it) }
    }

    override fun findByUserIdAndXpType(userId: Long, xpType: com.teamfair.moduleuser.domain.model.XpType): UserXpModel? {
        return userXpRepository.findByUserEntityIdAndXpType(userId, xpType)
            ?.let { UserXpMapper.toModel(it) }
    }

    override fun deleteById(id: Long) {
        userXpRepository.deleteById(id)
    }

    override fun existsById(id: Long): Boolean {
        return userXpRepository.existsById(id)
    }
} 