package com.illsang.quest.domain.model

import com.illsang.common.domain.model.BaseModel
import com.illsang.quest.domain.entity.QuizEntity
import java.time.LocalDateTime

data class QuizModel(
    val id: Long? = null,
    var question: String,
    var hint: String? = null,
    var sortOrder: Int = 0,
    val missionId: Long,
    override val createdBy: String? = null,
    override val createdAt: LocalDateTime? = null,
    override val updatedBy: String? = null,
    override val updatedAt: LocalDateTime? = null
) : BaseModel(createdBy, createdAt, updatedBy, updatedAt) {
    companion object {
        fun from(quiz: QuizEntity): QuizModel {
            return QuizModel(
                id = quiz.id,
                question = quiz.question,
                hint = quiz.hint,
                sortOrder = quiz.sortOrder,
                missionId = quiz.mission.id!!,
                createdBy = quiz.createdBy,
                createdAt = quiz.createdAt,
                updatedBy = quiz.updatedBy,
                updatedAt = quiz.updatedAt,
            )
        }
    }
}
