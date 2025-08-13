package com.illsang.user.dto.response

import java.time.LocalDateTime

data class CouponUserResponse(
    val id: Long,
    val userId: Long,
    val couponId: Long,
    val status: String? = null, // 예: ISSUED, USED, EXPIRED 등
    val issuedAt: LocalDateTime? = null,
    val usedAt: LocalDateTime? = null,
    val expiresAt: LocalDateTime? = null
)