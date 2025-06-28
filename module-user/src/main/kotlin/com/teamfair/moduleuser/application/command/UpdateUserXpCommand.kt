package com.teamfair.moduleuser.application.command

import com.teamfair.moduleuser.domain.model.XpType

data class UpdateUserXpCommand(
    val id: Long,
    val xpType: XpType? = null,
    val point: Int? = null
) 