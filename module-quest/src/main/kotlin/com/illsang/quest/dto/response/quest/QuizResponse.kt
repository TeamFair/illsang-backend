package com.illsang.quest.dto.response.quest

import com.illsang.quest.domain.model.quset.QuizModel
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
    val updatedAt: LocalDateTime?,
) {
    companion object {
        fun from(quizModel: QuizModel): QuizResponse {
            return QuizResponse(
                id = quizModel.id,
                question = quizModel.question,
                hint = quizModel.hint,
                sortOrder = quizModel.sortOrder,
                missionId = quizModel.missionId,
                createdBy = quizModel.createdBy,
                createdAt = quizModel.createdAt,
                updatedBy = quizModel.updatedBy,
                updatedAt = quizModel.updatedAt,
            )
        }
    }
}
