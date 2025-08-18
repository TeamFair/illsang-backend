package com.illsang.common.event.user.coupon

data class CouponPasswordVerificationOrThrowEvent (
    val couponId: Long,
    val password: String
)