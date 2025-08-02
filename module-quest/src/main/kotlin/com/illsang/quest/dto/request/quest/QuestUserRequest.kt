package com.illsang.quest.dto.request.quest

data class QuestUserRequest(
    val userId: String,
    val popularYn: Boolean? = null,
    val commercialAreaCode: String? = null,
    val orderReward: Boolean = false,
)
