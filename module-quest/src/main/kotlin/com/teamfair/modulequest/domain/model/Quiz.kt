package com.teamfair.modulequest.domain.model

data class Quiz(
    val id: Long? = null,
    var question: String,
    var hint : String? = null,
    var sortOrder: Int = 0,
    val answers: MutableList<QuizAnswer> = mutableListOf(),
    val userHistories: MutableList<UserQuizHistory> = mutableListOf(),
    val createdBy: String? = null,
    val createdAt: String? = null,
    val updatedBy: String? = null,
    val updatedAt: String? = null
) 