package com.illsang.modulequest.domain.model

import com.illsang.common.domain.model.BaseModel
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
) : BaseModel(createdBy, createdAt, updatedBy, updatedAt)
