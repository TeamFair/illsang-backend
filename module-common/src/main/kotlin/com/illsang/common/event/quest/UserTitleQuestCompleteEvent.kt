package com.illsang.common.event.quest


data class UserTitleQuestCompleteEvent(
    val userId: String,
    val maxStreak: Int
)