package com.illsang.modulequest.application.command

import java.time.LocalDateTime

data class CreateUserQuizHistoryCommand(
    val userId: Long,
    val answer: String? = null,
    val submittedAt: LocalDateTime? = null,
    val quizId: Long,
    val userMissionHistoryId: Long
)
