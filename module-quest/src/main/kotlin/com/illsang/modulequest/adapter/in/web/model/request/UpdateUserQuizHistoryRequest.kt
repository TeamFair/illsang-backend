package com.illsang.modulequest.adapter.`in`.web.model.request

import java.time.LocalDateTime

data class UpdateUserQuizHistoryRequest(
    val answer: String? = null,
    val submittedAt: LocalDateTime? = null
)
