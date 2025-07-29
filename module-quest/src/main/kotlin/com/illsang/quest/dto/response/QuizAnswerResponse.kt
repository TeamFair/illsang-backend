package com.illsang.quest.dto.response

import com.illsang.quest.domain.model.QuizAnswerModel
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
        fun from(quizAnswerModel: QuizAnswerModel): QuizAnswerResponse {
            return QuizAnswerResponse(
                id = quizAnswerModel.id,
                answer = quizAnswerModel.answer,
                sortOrder = quizAnswerModel.sortOrder,
                quizId = quizAnswerModel.quizId,
                createdBy = quizAnswerModel.createdBy,
                createdAt = quizAnswerModel.createdAt,
                updatedBy = quizAnswerModel.updatedBy,
                updatedAt = quizAnswerModel.updatedAt,
            )
        }
    }
}
