package com.illsang.user.dto.request

import com.illsang.user.enums.XpType

data class AddPointRequest(
    val xpType: XpType,
    val additionalPoint: Int
)
