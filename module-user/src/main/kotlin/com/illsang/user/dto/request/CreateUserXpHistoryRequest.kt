package com.illsang.user.dto.request

import com.illsang.user.enums.PointType

data class CreateUserXpHistoryRequest(
    val userId: Long,
    val pointType: PointType,
    val point: Int
)
