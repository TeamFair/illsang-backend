package com.illsang.moduleuser.adapter.`in`.web.model.request

import com.illsang.moduleuser.domain.model.XpType

data class UpdateUserXpHistoryRequest(
    val xpType: XpType? = null,
    val point: Int? = null
)
