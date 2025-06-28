package com.teamfair.moduleuser.adapter.`in`.web.model.request

import com.teamfair.moduleuser.domain.model.XpType

data class AddPointRequest(
    val xpType: XpType,
    val additionalPoint: Int
) 