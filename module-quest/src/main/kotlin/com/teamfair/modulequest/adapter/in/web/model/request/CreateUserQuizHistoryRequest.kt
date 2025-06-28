package com.teamfair.modulequest.adapter.`in`.web.model.request

import java.time.LocalDateTime

data class CreateUserQuizHistoryRequest(
    val userId: Long,
    val answer: String? = null,
    val submittedAt: LocalDateTime? = null,
    val quizId: Long,
    val userMissionHistoryId: Long
) 