package com.illsang.moduleuser.adapter.`in`.web.model.request

import com.illsang.moduleuser.domain.model.XpType

data class CreateUserXpRequest(
    val userId: Long,
    val xpType: XpType,
    val point: Int
)
