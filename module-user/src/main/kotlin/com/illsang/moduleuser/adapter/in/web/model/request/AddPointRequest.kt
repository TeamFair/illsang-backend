package com.illsang.moduleuser.adapter.`in`.web.model.request

import com.illsang.moduleuser.domain.model.XpType

data class AddPointRequest(
    val xpType: XpType,
    val additionalPoint: Int
)
