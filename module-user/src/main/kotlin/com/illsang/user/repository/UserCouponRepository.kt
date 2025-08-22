package com.illsang.user.repository

import com.illsang.user.domain.entity.UserCouponEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface UserCouponRepository : JpaRepository<UserCouponEntity, Long> {
    fun findAllByUserId(userId: String, pageable: Pageable): Page<UserCouponEntity>
}
