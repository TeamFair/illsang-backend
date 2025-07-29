package com.illsang.user.dto.request

import com.illsang.user.enums.XpType

data class CreateUserXpRequest(
    val userId: Long,
    val xpType: XpType,
    val point: Int
)
