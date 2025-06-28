package com.teamfair.modulequest.adapter.`in`.web.model.response

import com.teamfair.modulequest.domain.model.enums.QuestStatus
import java.time.LocalDateTime

data class UserQuestHistoryResponse(
    val id: Long,
    val userId: Long,
    val status: QuestStatus,
    val liked: Boolean,
    val disliked: Boolean,
    val viewCount: Int,
    val questId: Long,
    val createdBy: String?,
    val createdAt: LocalDateTime?,
    val updatedBy: String?,
    val updatedAt: LocalDateTime?
) 