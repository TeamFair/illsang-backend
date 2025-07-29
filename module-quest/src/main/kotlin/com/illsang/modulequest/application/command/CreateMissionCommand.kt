package com.illsang.modulequest.application.command

data class CreateMissionCommand(
    val type: String,
    val title: String,
    val sortOrder: Int = 0,
    val questId: Long
)
