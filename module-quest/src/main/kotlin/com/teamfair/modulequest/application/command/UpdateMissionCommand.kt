package com.teamfair.modulequest.application.command

data class UpdateMissionCommand(
    val id: Long,
    val type: String? = null,
    val title: String? = null,
    val sortOrder: Int? = null
) 