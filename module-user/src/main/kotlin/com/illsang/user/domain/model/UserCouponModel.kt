package com.illsang.user.domain.model

import com.illsang.common.event.user.coupon.CouponInfoGetEvent
import com.illsang.user.domain.entity.UserCouponEntity
import java.time.LocalDateTime


data class UserCouponModel(
    val id: Long?,
    val userId: String,
    val couponId: Long,
    val couponUseYn: Boolean,
    val couponExpireYn: Boolean,
    val usedAt: LocalDateTime?,
    val createdAt: LocalDateTime?,
    val updatedAt: LocalDateTime?,
    val couponName: String?,
    var couponStoreName: String? = null,
) {
    companion object {
        fun from(entity: UserCouponEntity, coupon: CouponModel?) = UserCouponModel(
            id = entity.id,
            userId = entity.userId,
            couponId = entity.couponId,
            couponUseYn = entity.couponUseYn,
            couponExpireYn = entity.couponExpireYn,
            usedAt = entity.usedAt,
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt,
            couponName = coupon?.name,
        )
    }
}

data class CouponModel(
    val name: String,
    val storeId: String,
){
    companion object{
        fun from(coupon : CouponInfoGetEvent.CouponInfo) = CouponModel(
            name = coupon.name,
            storeId = coupon.storeId
        )
    }
}
