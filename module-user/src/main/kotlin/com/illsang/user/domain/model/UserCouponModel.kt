package com.illsang.user.domain.model

import com.illsang.common.enums.CouponType
import com.illsang.common.event.user.coupon.CouponInfoGetEvent
import com.illsang.user.domain.entity.UserCouponEntity
import java.time.LocalDateTime


data class UserCouponModel(
    val id: Long?,
    val userId: String,
    val couponUseYn: Boolean,
    val couponExpireYn: Boolean,
    val usedAt: LocalDateTime?,
    val createdAt: LocalDateTime?,
    val updatedAt: LocalDateTime?,
    val coupon: CouponModel? = null,
) {
    companion object {
        fun from(entity: UserCouponEntity, coupon: CouponModel?) = UserCouponModel(
            id = entity.id,
            userId = entity.userId,
            couponUseYn = entity.couponUseYn,
            couponExpireYn = entity.couponExpireYn,
            usedAt = entity.usedAt,
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt,
            coupon = coupon,
        )
    }
}

data class CouponModel(
    val id: Long,
    val couponType: CouponType,
    val name: String,
    val imageId: String?,
    val storeName: String,
    val description: String?,
)
