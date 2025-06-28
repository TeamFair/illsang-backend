package com.teamfair.modulequest.application.command

data class UpdateQuizAnswerCommand(
    val id: Long,
    val answer: String? = null,
    val sortOrder: Int? = null
) 