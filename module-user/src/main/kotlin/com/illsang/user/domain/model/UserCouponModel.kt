package com.illsang.user.domain.model

import com.illsang.user.domain.entity.UserCouponEntity
import java.time.LocalDateTime


data class UserCouponModel(
    val id: Long?,
    val userId: Long,
    val couponId: Long,
    val couponUseYn: Boolean,
    val couponExpireYn: Boolean,
    val usedAt: LocalDateTime?,
    val createdAt: LocalDateTime?,
    val updatedAt: LocalDateTime?
) {
    companion object {
        fun from(entity: UserCouponEntity) = UserCouponModel(
            id = entity.id,
            userId = entity.userId,
            couponId = entity.couponId,
            couponUseYn = entity.couponUseYn,
            couponExpireYn = entity.couponExpireYn,
            usedAt = entity.usedAt,
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt
        )
    }
}
