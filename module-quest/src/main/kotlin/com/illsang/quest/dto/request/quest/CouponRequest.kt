package com.illsang.quest.dto.request.quest

import java.time.LocalDateTime

data class CreateCouponRequest(
    val name: String,
    val expiresAt: LocalDateTime? = null,
    val description: String? = null
)


