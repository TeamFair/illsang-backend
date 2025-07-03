package com.illsang.modulequest.adapter.`in`.web.model.request

data class CreateQuizRequest(
    val question: String,
    val hint: String? = null,
    val sortOrder: Int = 0,
    val missionId: Long
)
