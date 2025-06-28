package com.teamfair.modulequest.application.command

data class CreateQuizCommand(
    val missionId: Long,
    val question: String,
    val hint: String? = null,
    val sortOrder: Int = 0
) 