package com.illsang.common.port.coupon

interface CouponVerificationPort {
    fun verifyPassword(couponId: Long, password: String): Boolean
}