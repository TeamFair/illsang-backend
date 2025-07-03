package com.illsang.modulequest.adapter.`in`.web.model.request

data class UpdateQuizRequest(
    val question: String? = null,
    val hint: String? = null,
    val sortOrder: Int? = null
)
