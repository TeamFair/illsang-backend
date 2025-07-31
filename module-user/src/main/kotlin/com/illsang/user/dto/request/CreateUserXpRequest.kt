package com.illsang.user.dto.request

import com.illsang.user.enums.PointType

data class CreateUserXpRequest(
    val userId: Long,
    val pointType: PointType,
    val point: Int
)
