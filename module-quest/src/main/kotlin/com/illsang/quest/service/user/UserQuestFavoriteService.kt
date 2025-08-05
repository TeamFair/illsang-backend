package com.illsang.quest.service.user

import com.illsang.quest.domain.entity.user.UserQuestFavoriteEntity
import com.illsang.quest.repository.user.UserQuestFavoriteRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class UserQuestFavoriteService(
    private val userQuestFavoriteRepository: UserQuestFavoriteRepository,
) {

    fun findAllByQuestIdAndUserId(userId: String, questids: List<Long>): List<UserQuestFavoriteEntity> =
        this.userQuestFavoriteRepository.findAllByUserIdAndQuestIdIn(userId, questids)

    @Transactional
    fun createFavorite(userId: String, questId: Long) {
        this.userQuestFavoriteRepository.findByUserIdAndQuestId(userId, questId)?.let {
            throw IllegalArgumentException("Already exists favorite")
        }

        val questFavorite = UserQuestFavoriteEntity(
            userId = userId,
            questId = questId,
        )

        this.userQuestFavoriteRepository.save(questFavorite)
    }

    @Transactional
    fun deleteFavorite(userId: String, questId: Long) {
        val questFavorite = (this.userQuestFavoriteRepository.findByUserIdAndQuestId(userId, questId)
            ?: throw IllegalArgumentException("Favorite not found"))

        this.userQuestFavoriteRepository.delete(questFavorite)
    }

}
