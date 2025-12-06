package com.illsang.common.event.user.coupon

data class UserCouponCreateEvent(
    val userId: String,
    val couponId: Long?,
)