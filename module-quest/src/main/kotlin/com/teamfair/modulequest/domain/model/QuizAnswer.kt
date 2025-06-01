package com.teamfair.modulequest.domain.model

data class QuizAnswer(
    val id: Long? = null,
    var answer: String,
    var sortOrder: Int = 0,
    val createdBy: String? = null,
    val createdAt: String? = null,
    val updatedBy: String? = null,
    val updatedAt: String? = null
) 