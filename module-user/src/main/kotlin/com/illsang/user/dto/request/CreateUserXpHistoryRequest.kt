package com.illsang.user.dto.request

import com.illsang.user.enums.XpType

data class CreateUserXpHistoryRequest(
    val userId: Long,
    val xpType: XpType,
    val point: Int
)
