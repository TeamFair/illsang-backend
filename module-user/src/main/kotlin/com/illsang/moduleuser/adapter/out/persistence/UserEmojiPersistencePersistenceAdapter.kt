package com.illsang.moduleuser.adapter.out.persistence

import com.illsang.moduleuser.adapter.out.persistence.repository.UserEmojiRepository
import com.illsang.moduleuser.application.port.out.UserEmojiPersistencePort
import com.illsang.moduleuser.domain.mapper.UserEmojiMapper
import com.illsang.moduleuser.domain.model.UserEmojiModel
import org.springframework.stereotype.Component

@Component
class UserEmojiPersistencePersistenceAdapter(
    private val userEmojiRepository: UserEmojiRepository,
) : UserEmojiPersistencePort {


    /**
     * save emoji
     */
    override fun save(userEmoji: UserEmojiModel): UserEmojiModel {
        val entity = UserEmojiMapper.toEntity(userEmoji)
        val savedEntity = userEmojiRepository.save(entity)
        return UserEmojiMapper.toModel(savedEntity)
    }

    /**
     * find emoji by id
     */
    override fun findById(id: Long): UserEmojiModel? {
        return userEmojiRepository.findById(id)
            .map { UserEmojiMapper.toModel(it) }
            .orElse(null)
    }

    /**
     * find emoji by user id
     */
    override fun findByUserId(userId: Long): List<UserEmojiModel> {
        return userEmojiRepository.findByUserId(userId)
            .map { UserEmojiMapper.toModel(it) }
    }

    /**
     * delete emoji by id
     */
    override fun deleteById(id: Long) {
        userEmojiRepository.deleteById(id)
    }

    /**
     * check if emoji exists by id
     */
    override fun existsById(id: Long): Boolean {
        return userEmojiRepository.existsById(id)
    }
}
