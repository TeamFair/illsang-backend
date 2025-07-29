package com.illsang.modulequest.application.command

import com.illsang.modulequest.domain.model.enums.QuestStatus

data class UpdateUserQuestHistoryCommand(
    val id: Long,
    val status: QuestStatus? = null,
    val liked: Boolean? = null,
    val disliked: Boolean? = null,
    val viewCount: Int? = null
)
