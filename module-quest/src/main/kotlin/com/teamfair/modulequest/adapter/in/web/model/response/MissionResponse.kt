package com.teamfair.modulequest.adapter.`in`.web.model.response

import com.teamfair.modulequest.domain.model.Mission
import java.time.LocalDateTime

data class MissionResponse(
    val id: Long?,
    val type: String,
    val title: String,
    val sortOrder: Int,
    val questId: Long,
    val createdBy: String?,
    val createdAt: LocalDateTime?,
    val updatedBy: String?,
    val updatedAt: LocalDateTime?
) {
    companion object {
        fun from(mission: Mission): MissionResponse {
            return MissionResponse(
                id = mission.id,
                type = mission.type,
                title = mission.title,
                sortOrder = mission.sortOrder,
                questId = mission.questId,
                createdBy = mission.createdBy,
                createdAt = mission.createdAt,
                updatedBy = mission.updatedBy,
                updatedAt = mission.updatedAt
            )
        }
    }
} 