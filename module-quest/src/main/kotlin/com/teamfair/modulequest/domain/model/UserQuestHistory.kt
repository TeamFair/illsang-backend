package com.teamfair.modulequest.domain.model

import com.teamfair.modulequest.domain.model.enums.QuestStatus

data class UserQuestHistory(
    val id: Long? = null,
    val userId: Long,
    var status: QuestStatus = QuestStatus.PROGRESSING,
    var liked: Boolean = false,
    var disliked: Boolean = false,
    var viewCount: Int = 0,
    val missionHistories: MutableList<UserMissionHistory> = mutableListOf(),
    val createdBy: String? = null,
    val createdAt: String? = null,
    val updatedBy: String? = null,
    val updatedAt: String? = null
) 