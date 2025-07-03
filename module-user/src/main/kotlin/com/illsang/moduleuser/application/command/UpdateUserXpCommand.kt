package com.illsang.moduleuser.application.command

import com.illsang.moduleuser.domain.model.XpType

data class UpdateUserXpCommand(
    val id: Long,
    val xpType: XpType? = null,
    val point: Int? = null
)
