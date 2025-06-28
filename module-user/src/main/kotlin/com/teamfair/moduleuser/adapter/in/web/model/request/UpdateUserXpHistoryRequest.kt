package com.teamfair.moduleuser.adapter.`in`.web.model.request

import com.teamfair.moduleuser.domain.model.XpType

data class UpdateUserXpHistoryRequest(
    val xpType: XpType? = null,
    val point: Int? = null
) 