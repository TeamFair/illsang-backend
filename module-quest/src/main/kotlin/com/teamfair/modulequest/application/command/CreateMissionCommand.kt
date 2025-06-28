package com.teamfair.modulequest.application.command

data class CreateMissionCommand(
    val questId: Long,
    val type: String,
    val title: String,
    val sortOrder: Int = 0
) 