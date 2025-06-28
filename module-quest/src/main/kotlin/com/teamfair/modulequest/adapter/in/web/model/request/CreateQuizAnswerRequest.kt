package com.teamfair.modulequest.adapter.`in`.web.model.request

data class CreateQuizAnswerRequest(
    val answer: String,
    val sortOrder: Int = 0,
    val quizId: Long
) 