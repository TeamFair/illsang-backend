package com.illsang.quest.dto.request.history

data class ChallengeCreateRequest(
    val imageId: String?,
    val quizId: Long?,
    val answer: String?,
)
