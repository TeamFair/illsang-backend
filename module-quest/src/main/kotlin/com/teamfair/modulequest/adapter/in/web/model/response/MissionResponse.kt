package com.teamfair.modulequest.adapter.`in`.web.model.response

import com.teamfair.modulequest.domain.model.MissionModel
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
        fun from(missionModel: MissionModel): MissionResponse {
            return MissionResponse(
                id = missionModel.id,
                type = missionModel.type,
                title = missionModel.title,
                sortOrder = missionModel.sortOrder,
                questId = missionModel.questId,
                createdBy = missionModel.createdBy,
                createdAt = missionModel.createdAt,
                updatedBy = missionModel.updatedBy,
                updatedAt = missionModel.updatedAt
            )
        }
    }
} 