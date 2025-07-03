package com.illsang.modulequest.application.command

data class UpdateQuizCommand(
    val id: Long,
    val question: String? = null,
    val hint: String? = null,
    val sortOrder: Int? = null
)
