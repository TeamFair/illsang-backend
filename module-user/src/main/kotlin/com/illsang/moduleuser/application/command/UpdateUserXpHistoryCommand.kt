package com.illsang.moduleuser.application.command

import com.illsang.moduleuser.domain.model.XpType

data class UpdateUserXpHistoryCommand(
    val id: Long,
    val xpType: XpType? = null,
    val point: Int? = null
)
