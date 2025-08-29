package com.illsang.user.repository

import com.illsang.user.domain.entity.UserTitleEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserTitleRepository : JpaRepository<UserTitleEntity, Long>{
    fun findAllByUserId(userId: String): List<UserTitleEntity>
    fun existsByUserIdAndTitleId(userId: String, titleId: String): Boolean
    fun findByUserIdAndId(userId: String, id: Long): UserTitleEntity?
}
