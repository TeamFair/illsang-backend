package com.illsang.user.dto.request

import com.illsang.user.enums.PointType

data class UpdateUserXpRequest(
    val pointType: PointType? = null,
    val point: Int? = null
)
