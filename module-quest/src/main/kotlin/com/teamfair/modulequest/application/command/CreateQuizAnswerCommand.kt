package com.teamfair.modulequest.application.command

data class CreateQuizAnswerCommand(
    val quizId: Long,
    val answer: String,
    val sortOrder: Int = 0
) 