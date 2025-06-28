package com.teamfair.moduleuser.adapter.out.persistence.repository

import com.teamfair.moduleuser.adapter.out.persistence.entity.UserXpHistoryEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserXpHistoryRepository : JpaRepository<UserXpHistoryEntity, Long> {
    fun findByUserEntityId(userId: Long): List<UserXpHistoryEntity>
} 