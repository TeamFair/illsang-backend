package com.illsang.common.event.user.point

import com.illsang.common.enums.PointType

data class UserPointCreateEvent(
    val seasonId: Long,
    val userId: String,
    val questId: Long,
    val request: List<UserPointCreateRequest>,
    val userQuestHistoryId: Long? = null
)

data class UserPointCreateRequest(
    val metroAreaCode: String,
    val commercialAreaCode: String,
    val pointType: PointType,
    val point: Int,
)
