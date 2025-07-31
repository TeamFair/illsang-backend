package com.illsang.user.dto.request

import com.illsang.user.enums.PointType

data class AddPointRequest(
    val pointType: PointType,
    val additionalPoint: Int
)
