package com.teamfair.modulequest.adapter.`in`.web.model.response

import com.teamfair.modulequest.domain.model.QuizAnswer
import java.time.LocalDateTime

data class QuizAnswerResponse(
    val id: Long?,
    val answer: String,
    val sortOrder: Int,
    val quizId: Long,
    val createdBy: String?,
    val createdAt: LocalDateTime?,
    val updatedBy: String?,
    val updatedAt: LocalDateTime?
) {
    companion object {
        fun from(quizAnswer: QuizAnswer): QuizAnswerResponse {
            return QuizAnswerResponse(
                id = quizAnswer.id,
                answer = quizAnswer.answer,
                sortOrder = quizAnswer.sortOrder,
                quizId = quizAnswer.quizId,
                createdBy = quizAnswer.createdBy,
                createdAt = quizAnswer.createdAt,
                updatedBy = quizAnswer.updatedBy,
                updatedAt = quizAnswer.updatedAt
            )
        }
    }
} 