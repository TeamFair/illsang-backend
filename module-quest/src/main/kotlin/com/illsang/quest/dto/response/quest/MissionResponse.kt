package com.illsang.quest.dto.response.quest

import com.illsang.quest.domain.model.quset.MissionModel
import com.illsang.quest.enums.MissionType
import java.time.LocalDateTime

data class MissionResponse(
    val id: Long?,
    val type: MissionType,
    val sortOrder: Int?,
    val questId: Long,
    val createdBy: String?,
    val createdAt: LocalDateTime?,
    val updatedBy: String?,
    val updatedAt: LocalDateTime?,
) {
    companion object {
        fun from(missionModel: MissionModel): MissionResponse {
            return MissionResponse(
                id = missionModel.id,
                type = missionModel.type,
                sortOrder = missionModel.sortOrder,
                questId = missionModel.questId,
                createdBy = missionModel.createdBy,
                createdAt = missionModel.createdAt,
                updatedBy = missionModel.updatedBy,
                updatedAt = missionModel.updatedAt,
            )
        }
    }
}
