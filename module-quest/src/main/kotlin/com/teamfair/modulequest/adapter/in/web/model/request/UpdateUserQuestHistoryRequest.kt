package com.teamfair.modulequest.adapter.`in`.web.model.request

import com.teamfair.modulequest.domain.model.enums.QuestStatus

data class UpdateUserQuestHistoryRequest(
    val status: QuestStatus? = null,
    val liked: Boolean? = null,
    val disliked: Boolean? = null,
    val viewCount: Int? = null
) 