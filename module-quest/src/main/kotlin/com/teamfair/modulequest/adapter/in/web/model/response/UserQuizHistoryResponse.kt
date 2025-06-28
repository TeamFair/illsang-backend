package com.teamfair.modulequest.adapter.`in`.web.model.response

import java.time.LocalDateTime

data class UserQuizHistoryResponse(
    val id: Long,
    val userId: Long,
    val answer: String?,
    val submittedAt: LocalDateTime?,
    val quizId: Long,
    val userMissionHistoryId: Long,
    val createdBy: String?,
    val createdAt: LocalDateTime?,
    val updatedBy: String?,
    val updatedAt: LocalDateTime?
)