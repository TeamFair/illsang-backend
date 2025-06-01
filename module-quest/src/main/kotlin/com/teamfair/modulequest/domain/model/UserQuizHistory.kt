package com.teamfair.modulequest.domain.model

import java.time.LocalDateTime

data class UserQuizHistory(
    val id: Long? = null,
    val userId: Long,
    var answer: String? = null,
    var submittedAt: LocalDateTime? = null,
    val createdBy: String? = null,
    val createdAt: String? = null,
    val updatedBy: String? = null,
    val updatedAt: String? = null
) 