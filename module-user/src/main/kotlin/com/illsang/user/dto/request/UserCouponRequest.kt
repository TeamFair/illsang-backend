package com.illsang.user.dto.request


data class UserCouponCreateRequest(
    val userId: String,
    val couponId: Long,
    val couponUseYn: Boolean = false,
    val couponExpireYn: Boolean = false
)

data class UserCouponUpdateRequest(
    val couponUseYn: Boolean? = null,
    val couponExpireYn: Boolean? = null
)

data class CouponPasswordVerifyRequest(
    val password: String
)