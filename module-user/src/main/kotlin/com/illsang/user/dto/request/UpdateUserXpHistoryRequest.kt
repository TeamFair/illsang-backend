package com.illsang.user.dto.request

import com.illsang.user.enums.PointType

data class UpdateUserXpHistoryRequest(
    val pointType: PointType? = null,
    val point: Int? = null
)
