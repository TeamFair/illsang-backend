package com.teamfair.modulequest.adapter.`in`.web.model.request

data class UpdateQuizRequest(
    val question: String? = null,
    val hint: String? = null,
    val sortOrder: Int? = null
) 