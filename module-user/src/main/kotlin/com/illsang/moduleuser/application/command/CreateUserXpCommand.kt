package com.illsang.moduleuser.application.command

import com.illsang.moduleuser.domain.model.XpType

data class CreateUserXpCommand(
    val userId: Long,
    val xpType: XpType,
    val point: Int
)
