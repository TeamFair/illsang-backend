package com.teamfair.modulequest.application.command

data class CreateQuizAnswerCommand(
    val answer: String,
    val sortOrder: Int = 0,
    val quizId: Long
) 