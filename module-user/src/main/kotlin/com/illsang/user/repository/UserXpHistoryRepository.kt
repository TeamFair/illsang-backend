package com.illsang.user.repository

import com.illsang.user.domain.entity.UserXpHistoryEntity
import com.illsang.user.enums.XpType
import org.springframework.data.jpa.repository.JpaRepository

interface UserXpHistoryRepository : JpaRepository<UserXpHistoryEntity, Long> {
    fun findByUserEntityId(userId: Long): List<UserXpHistoryEntity>
    fun findByUserEntityIdAndXpType(userId: Long, xpType: XpType): List<UserXpHistoryEntity>
}
