package com.teamfair.modulequest.domain.model

import com.illsang.common.model.BaseModel
import java.time.LocalDateTime

data class UserQuizHistory(
    val id: Long? = null,
    val userId: Long,
    var answer: String? = null,
    var submittedAt: LocalDateTime? = null,
    val quizId: Long,
    val userMissionHistoryId: Long,
    override val createdBy: String? = null,
    override val createdAt: LocalDateTime? = null,
    override val updatedBy: String? = null,
    override val updatedAt: LocalDateTime? = null
) : BaseModel(createdBy, createdAt, updatedBy, updatedAt) 