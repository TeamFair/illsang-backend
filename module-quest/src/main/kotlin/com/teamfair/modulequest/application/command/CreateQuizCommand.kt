package com.teamfair.modulequest.application.command

data class CreateQuizCommand(
    val question: String,
    val hint: String? = null,
    val sortOrder: Int = 0,
    val missionId: Long
) 