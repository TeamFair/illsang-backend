package com.illsang.modulequest.application.command

import java.time.LocalDateTime

data class UpdateUserQuizHistoryCommand(
    val id: Long,
    val answer: String? = null,
    val submittedAt: LocalDateTime? = null
)
