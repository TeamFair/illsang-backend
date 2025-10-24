package com.illsang.quest.dto.response.quest

import com.illsang.quest.domain.model.quset.QuizHistoryModel
import java.time.LocalDateTime

data class QuizHistoryResponse(
    val id: Long?,
    val question: String?,
    val answers: List<QuizAnswerResponse>?,
    val userAnswer: String?,
    val createdAt: LocalDateTime?
) {
    companion object{
        fun from(quizHistoryModel: QuizHistoryModel): QuizHistoryResponse {
            return QuizHistoryResponse(
                id = quizHistoryModel.id,
                question = quizHistoryModel.question,
                answers = quizHistoryModel.answers?.map { QuizAnswerResponse.from(it) },
                userAnswer = quizHistoryModel.userAnswer,
                createdAt = quizHistoryModel.createdAt
            )
        }
    }
}