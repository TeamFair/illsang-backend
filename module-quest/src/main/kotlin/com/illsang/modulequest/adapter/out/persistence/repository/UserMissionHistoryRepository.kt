package com.illsang.modulequest.adapter.out.persistence.repository

import com.illsang.modulequest.adapter.out.persistence.entity.UserMissionHistoryEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserMissionHistoryRepository : JpaRepository<UserMissionHistoryEntity, Long> {
    fun findByUserId(userId: Long): List<UserMissionHistoryEntity>
    fun findByMissionId(missionId: Long): List<UserMissionHistoryEntity>
}
