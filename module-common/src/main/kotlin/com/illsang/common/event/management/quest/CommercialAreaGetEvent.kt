package com.illsang.common.event.management.quest

data class CompletedQuestHistoryCountGetEvent(
    val seasonId: Long?,
    val userId: String,
) {
    var response: Long = 0L
}

