package com.illsang.quest.repository.user

import com.illsang.quest.domain.entity.user.UserMissionHistoryCommentEntity
import org.springframework.data.jpa.repository.JpaRepository

interface MissionHistoryCommentRepository : JpaRepository<UserMissionHistoryCommentEntity, Long> {

    fun findAllByMissionHistoryId(userMissionHistoryId: Long): List<UserMissionHistoryCommentEntity>
}