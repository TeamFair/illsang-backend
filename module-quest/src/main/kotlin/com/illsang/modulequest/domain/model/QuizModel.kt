package com.illsang.modulequest.domain.model

import com.illsang.common.domain.model.BaseModel
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
) : BaseModel(createdBy, createdAt, updatedBy, updatedAt)
