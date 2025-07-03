package com.illsang.moduleuser.adapter.out.persistence.repository

import com.illsang.moduleuser.adapter.out.persistence.entity.UserXpEntity
import com.illsang.moduleuser.domain.model.XpType
import org.springframework.data.jpa.repository.JpaRepository

interface UserXpRepository : JpaRepository<UserXpEntity, Long> {
    fun findByUserEntityId(userId: Long): List<UserXpEntity>
    fun findByUserEntityIdAndXpType(userId: Long, xpType: XpType): UserXpEntity?
}
