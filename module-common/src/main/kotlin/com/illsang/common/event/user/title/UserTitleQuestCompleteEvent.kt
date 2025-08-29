package com.illsang.common.event.user.title


data class UserTitleQuestCompleteEvent(
    val userId: String,
    val maxStreak: Int
)