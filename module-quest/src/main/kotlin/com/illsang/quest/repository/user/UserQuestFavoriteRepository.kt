package com.illsang.quest.repository.user

import com.illsang.quest.domain.entity.user.UserQuestFavoriteEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserQuestFavoriteRepository : JpaRepository<UserQuestFavoriteEntity, Long> {
    fun findAllByUserIdAndQuestIdIn(userId: String, questId: List<Long>): List<UserQuestFavoriteEntity>
    fun findByUserIdAndQuestId(userId: String, questId: Long): UserQuestFavoriteEntity?
}
