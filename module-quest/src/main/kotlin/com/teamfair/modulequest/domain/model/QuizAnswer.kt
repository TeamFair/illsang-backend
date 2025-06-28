package com.teamfair.modulequest.domain.model

import com.illsang.common.model.BaseModel
import java.time.LocalDateTime

data class QuizAnswer(
    val id: Long? = null,
    var answer: String,
    var sortOrder: Int = 0,
    val quizId: Long,
    override val createdBy: String? = null,
    override val createdAt: LocalDateTime? = null,
    override val updatedBy: String? = null,
    override val updatedAt: LocalDateTime? = null
) : BaseModel(createdBy, createdAt, updatedBy, updatedAt) 