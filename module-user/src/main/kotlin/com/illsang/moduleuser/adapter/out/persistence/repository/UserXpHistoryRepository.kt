package com.illsang.moduleuser.adapter.out.persistence.repository

import com.illsang.moduleuser.adapter.out.persistence.entity.UserXpHistoryEntity
import com.illsang.moduleuser.domain.model.XpType
import org.springframework.data.jpa.repository.JpaRepository

interface UserXpHistoryRepository : JpaRepository<UserXpHistoryEntity, Long> {
    fun findByUserEntityId(userId: Long): List<UserXpHistoryEntity>
    fun findByUserEntityIdAndXpType(userId: Long, xpType: XpType): List<UserXpHistoryEntity>
}
