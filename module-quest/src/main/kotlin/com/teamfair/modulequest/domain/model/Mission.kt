package com.teamfair.modulequest.domain.model

data class Mission(
    val id: Long? = null,
    var title: String,
    var description: String? = null,
    var sortOrder: Int = 0,
    val quizzes: MutableList<Quiz> = mutableListOf(),
    val userHistories: MutableList<UserMissionHistory> = mutableListOf(),
    val createdBy: String? = null,
    val createdAt: String? = null,
    val updatedBy: String? = null,
    val updatedAt: String? = null
) 