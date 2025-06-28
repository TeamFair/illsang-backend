package com.teamfair.moduleuser.adapter.`in`.web.model.request

import com.teamfair.moduleuser.domain.model.XpType

data class CreateUserXpRequest(
    val userId: Long,
    val xpType: XpType,
    val point: Int
) 