package com.illsang.user.dto.response

import com.illsang.user.domain.model.UserCouponModel
import java.time.LocalDateTime

data class UserCouponResponse(
    val id: Long,
    val userId: Long,
    val couponId: Long,
    val couponUseYn: Boolean,
    val couponExpireYn: Boolean,
    val usedAt: LocalDateTime?
) {
    companion object {
        fun from(model: UserCouponModel) = UserCouponResponse(
            id = model.id!!,
            userId = model.userId,
            couponId = model.couponId,
            couponUseYn = model.couponUseYn,
            couponExpireYn = model.couponExpireYn,
            usedAt = model.usedAt
        )
    }
}

data class CouponPasswordVerifyResponse(
    val success: Boolean
)