package com.teamfair.moduleuser.adapter.out.persistence

import com.teamfair.moduleuser.adapter.out.persistence.repository.UserRepository
import com.teamfair.moduleuser.adapter.out.persistence.repository.UserXpHistoryRepository
import com.teamfair.moduleuser.application.port.out.UserXpHistoryPersistencePort
import com.teamfair.moduleuser.domain.mapper.UserXpHistoryMapper
import com.teamfair.moduleuser.domain.model.UserXpHistoryModel
import org.springframework.stereotype.Component

@Component
class UserXpHistoryPersistenceAdapter(
    private val userXpHistoryRepository: UserXpHistoryRepository,
    private val userRepository: UserRepository
) : UserXpHistoryPersistencePort {

    override fun save(userXpHistory: UserXpHistoryModel): UserXpHistoryModel {
        val userEntity = userRepository.findById(userXpHistory.userId)
            .orElseThrow { IllegalArgumentException("User not found with id: ${userXpHistory.userId}") }
        
        val entity = UserXpHistoryMapper.toEntity(userXpHistory, userEntity)
        val savedEntity = userXpHistoryRepository.save(entity)
        return UserXpHistoryMapper.toModel(savedEntity)
    }

    override fun findById(id: Long): UserXpHistoryModel? {
        return userXpHistoryRepository.findById(id)
            .map { UserXpHistoryMapper.toModel(it) }
            .orElse(null)
    }

    override fun findByUserId(userId: Long): List<UserXpHistoryModel> {
        return userXpHistoryRepository.findByUserEntityId(userId)
            .map { UserXpHistoryMapper.toModel(it) }
    }

    override fun findByUserIdAndXpType(userId: Long, xpType: com.teamfair.moduleuser.domain.model.XpType): List<UserXpHistoryModel> {
        return userXpHistoryRepository.findByUserEntityIdAndXpType(userId, xpType)
            .map { UserXpHistoryMapper.toModel(it) }
    }

    override fun deleteById(id: Long) {
        userXpHistoryRepository.deleteById(id)
    }

    override fun existsById(id: Long): Boolean {
        return userXpHistoryRepository.existsById(id)
    }
} 