package com.teamfair.moduleuser.application.command

import com.teamfair.moduleuser.domain.model.XpType

data class CreateUserXpHistoryCommand(
    val userId: Long,
    val xpType: XpType,
    val point: Int
) 