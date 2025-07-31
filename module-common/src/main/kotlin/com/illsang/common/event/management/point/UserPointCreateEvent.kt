package com.illsang.common.event.management.point

data class UserPointCreateEvent (
    val userId: String,
    val request: List<UserPointCreateRequest>,
)

data class UserPointCreateRequest (
    val pointType: String,
    val point: Int,
)
