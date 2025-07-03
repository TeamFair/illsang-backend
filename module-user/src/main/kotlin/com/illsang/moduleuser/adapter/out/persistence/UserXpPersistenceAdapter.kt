package com.illsang.moduleuser.adapter.out.persistence

import com.illsang.moduleuser.adapter.out.persistence.repository.UserRepository
import com.illsang.moduleuser.adapter.out.persistence.repository.UserXpRepository
import com.illsang.moduleuser.application.port.out.UserXpPersistencePort
import com.illsang.moduleuser.domain.mapper.UserXpMapper
import com.illsang.moduleuser.domain.model.UserXpModel
import org.springframework.stereotype.Component

@Component
class UserXpPersistenceAdapter(
    private val userXpRepository: UserXpRepository,
    private val userRepository: UserRepository
) : UserXpPersistencePort {

    /**
     * save user xp
     */
    override fun save(userXp: UserXpModel): UserXpModel {
        val userEntity = userRepository.findById(userXp.userId)
            .orElseThrow { IllegalArgumentException("User not found with id: ${userXp.userId}") }

        val entity = UserXpMapper.toEntity(userXp, userEntity)
        val savedEntity = userXpRepository.save(entity)
        return UserXpMapper.toModel(savedEntity)
    }

    /**
     * find user xp by id
     */
    override fun findById(id: Long): UserXpModel? {
        return userXpRepository.findById(id)
            .map { UserXpMapper.toModel(it) }
            .orElse(null)
    }

    /**
     * find user xp by user id
     */
    override fun findByUserId(userId: Long): List<UserXpModel> {
        return userXpRepository.findByUserEntityId(userId)
            .map { UserXpMapper.toModel(it) }
    }

    /**
     * find user xp by user id and xp type
     */
    override fun findByUserIdAndXpType(userId: Long, xpType: com.illsang.moduleuser.domain.model.XpType): UserXpModel? {
        return userXpRepository.findByUserEntityIdAndXpType(userId, xpType)
            ?.let { UserXpMapper.toModel(it) }
    }

    /**
     * delete user xp
     */
    override fun deleteById(id: Long) {
        userXpRepository.deleteById(id)
    }

    /**
     * check if user xp exists by id
     */
    override fun existsById(id: Long): Boolean {
        return userXpRepository.existsById(id)
    }
}
