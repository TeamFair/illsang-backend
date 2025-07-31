package com.illsang.quest.domain.model.quset

import com.illsang.common.domain.model.BaseModel
import com.illsang.quest.domain.entity.quest.QuizAnswerEntity
import java.time.LocalDateTime

data class QuizAnswerModel(
    val id: Long? = null,
    var answer: String,
    var sortOrder: Int = 0,
    val quizId: Long,
    override val createdBy: String? = null,
    override val createdAt: LocalDateTime? = null,
    override val updatedBy: String? = null,
    override val updatedAt: LocalDateTime? = null
) : BaseModel(createdBy, createdAt, updatedBy, updatedAt) {

    companion object {
        fun from(quizAnswer: QuizAnswerEntity): QuizAnswerModel {
            return QuizAnswerModel(
                id = quizAnswer.id,
                answer = quizAnswer.answer,
                sortOrder = quizAnswer.sortOrder,
                quizId = quizAnswer.quiz.id!!,
                createdBy = quizAnswer.createdBy,
                createdAt = quizAnswer.createdAt,
                updatedBy = quizAnswer.updatedBy,
                updatedAt = quizAnswer.updatedAt,
            )
        }
    }

}
