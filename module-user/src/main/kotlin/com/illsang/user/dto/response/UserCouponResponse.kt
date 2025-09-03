package com.illsang.user.dto.response

import com.illsang.common.enums.CouponType
import com.illsang.user.domain.model.UserCouponModel
import java.time.LocalDateTime

data class UserCouponResponse(
    val id: Long,
    val userId: String,
    val couponUseYn: Boolean,
    val couponExpireYn: Boolean,
    val usedAt: LocalDateTime?,
    val coupon: CouponResponse,

    ) {
    companion object {
        fun from(model: UserCouponModel) = UserCouponResponse(
            id = model.id!!,
            userId = model.userId,
            couponUseYn = model.couponUseYn,
            couponExpireYn = model.couponExpireYn,
            usedAt = model.usedAt,
            coupon = CouponResponse(
                couponId = model.coupon!!.id,
                couponType = model.coupon.couponType,
                name = model.coupon.name,
                imageId = model.coupon.imageId,
                storeName = model.coupon.storeName,
                description = model.coupon.description
            ),
        )
    }
}

data class CouponPasswordVerifyResponse(
    val success: Boolean
)

data class CouponResponse(
    val couponId: Long,
    val couponType: CouponType,
    val name: String,
    val imageId: String?,
    val storeName: String,
    val description: String?,
)
