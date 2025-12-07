package com.illsang.quest.dto.request.user

data class ChallengeCreateRequest(
    val missionId: Long,
    val imageId: String?,
    val quizId: Long?,
    val answer: String?,
)

