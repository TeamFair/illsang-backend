package com.illsang.user.dto.request

import com.illsang.user.enums.XpType

data class UpdateUserXpHistoryRequest(
    val xpType: XpType? = null,
    val point: Int? = null
)
