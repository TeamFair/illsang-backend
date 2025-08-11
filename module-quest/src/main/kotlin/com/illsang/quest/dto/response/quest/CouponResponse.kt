package com.illsang.quest.dto.response.quest

import java.time.LocalDateTime

data class CouponResponse(
    val id: Long,
    val name: String,
    val expiresAt: LocalDateTime?,
    val description: String?
)