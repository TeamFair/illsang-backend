package com.illsang.common.event.management.point

import com.illsang.common.enums.PointType

data class UserPointCreateEvent (
    val userId: String,
    val questId: Long,
    val request: List<UserPointCreateRequest>,
)

data class UserPointCreateRequest (
    val metroAreaCode: String,
    val commercialAreaCode: String,
    val pointType: PointType,
    val point: Int,
)
