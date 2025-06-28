package com.teamfair.modulequest.adapter.`in`.web.model.response

import com.teamfair.modulequest.domain.model.Quiz
import java.time.LocalDateTime

data class QuizResponse(
    val id: Long?,
    val question: String,
    val hint: String?,
    val sortOrder: Int,
    val missionId: Long,
    val createdBy: String?,
    val createdAt: LocalDateTime?,
    val updatedBy: String?,
    val updatedAt: LocalDateTime?
) {
    companion object {
        fun from(quiz: Quiz): QuizResponse {
            return QuizResponse(
                id = quiz.id,
                question = quiz.question,
                hint = quiz.hint,
                sortOrder = quiz.sortOrder,
                missionId = quiz.missionId,
                createdBy = quiz.createdBy,
                createdAt = quiz.createdAt,
                updatedBy = quiz.updatedBy,
                updatedAt = quiz.updatedAt
            )
        }
    }
} 