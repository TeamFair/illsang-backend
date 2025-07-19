package com.illsang.user.repository

import com.illsang.user.domain.entity.UserXpEntity
import com.illsang.user.enums.XpType
import org.springframework.data.jpa.repository.JpaRepository

interface UserXpRepository : JpaRepository<UserXpEntity, Long> {
    fun findByUserEntityId(userId: Long): List<UserXpEntity>
    fun findByUserEntityIdAndXpType(userId: Long, xpType: XpType): UserXpEntity?
}
