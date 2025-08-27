package com.illsang.common.event.quest

data class TitleQuestCompleteEvent(
    val userId: String,
    val maxStreak: Int
)