package com.illsang.quest.dto.response

import com.illsang.quest.domain.model.QuizDetailModel
import com.illsang.quest.domain.model.QuizModel
import java.time.LocalDateTime

data class QuizDetailResponse(
    val id: Long?,
    val question: String,
    val hint: String?,
    val sortOrder: Int,
    val missionId: Long,
    val answers: List<QuizAnswerResponse>,
    val createdBy: String?,
    val createdAt: LocalDateTime?,
    val updatedBy: String?,
    val updatedAt: LocalDateTime?,
) {
    companion object {
        fun from(quizModel: QuizDetailModel): QuizDetailResponse {
            return QuizDetailResponse(
                id = quizModel.id,
                question = quizModel.question,
                hint = quizModel.hint,
                sortOrder = quizModel.sortOrder,
                missionId = quizModel.missionId,
                answers = quizModel.answers.map { QuizAnswerResponse.from(it) },
                createdBy = quizModel.createdBy,
                createdAt = quizModel.createdAt,
                updatedBy = quizModel.updatedBy,
                updatedAt = quizModel.updatedAt,
            )
        }
    }
}
