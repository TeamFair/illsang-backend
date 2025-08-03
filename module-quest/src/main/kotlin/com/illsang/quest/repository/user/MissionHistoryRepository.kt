package com.illsang.quest.repository.user

import com.illsang.quest.domain.entity.user.UserMissionHistoryEntity
import org.springframework.data.jpa.repository.JpaRepository

interface MissionHistoryRepository : JpaRepository<UserMissionHistoryEntity, Long> {
    fun findTop3ByMissionIdOrderByLikeCountDesc(questId: Long): List<UserMissionHistoryEntity>
}
