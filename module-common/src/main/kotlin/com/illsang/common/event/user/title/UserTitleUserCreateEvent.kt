package com.illsang.common.event.user.title

data class UserTitleUserCreateEvent (
    val userId: String,
    val titleId: String,
)