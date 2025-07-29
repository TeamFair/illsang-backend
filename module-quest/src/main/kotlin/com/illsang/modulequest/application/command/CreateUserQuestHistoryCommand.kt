package com.illsang.modulequest.application.command

import com.illsang.modulequest.domain.model.enums.QuestStatus

data class CreateUserQuestHistoryCommand(
    val userId: Long,
    val status: QuestStatus = QuestStatus.PROGRESSING,
    val liked: Boolean = false,
    val disliked: Boolean = false,
    val viewCount: Int = 0,
    val questId: Long
)
