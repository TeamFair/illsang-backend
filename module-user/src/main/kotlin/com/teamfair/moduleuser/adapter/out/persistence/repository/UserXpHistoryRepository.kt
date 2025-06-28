package com.teamfair.moduleuser.adapter.out.persistence.repository

import com.teamfair.moduleuser.adapter.out.persistence.entity.UserXpHistoryEntity
import com.teamfair.moduleuser.domain.model.XpType
import org.springframework.data.jpa.repository.JpaRepository

interface UserXpHistoryRepository : JpaRepository<UserXpHistoryEntity, Long> {
    fun findByUserEntityId(userId: Long): List<UserXpHistoryEntity>
    fun findByUserEntityIdAndXpType(userId: Long, xpType: XpType): List<UserXpHistoryEntity>
} 