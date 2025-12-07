package com.illsang.quest.dto.response.quest

import com.illsang.quest.domain.model.quset.MissionLabelModel
import com.illsang.quest.enums.MissionLabelType
import java.time.LocalDateTime

data class MissionLabelResponse(
    val id: Long?,
    val type: MissionLabelType,
    val label: String,
    val missionId: Long,
    val createdBy: String?,
    val createdAt: LocalDateTime?,
    val updatedBy: String?,
    val updatedAt: LocalDateTime?,
) {
    companion object {
        fun from(missionLabelModel: MissionLabelModel): MissionLabelResponse {
            return MissionLabelResponse(
                id = missionLabelModel.id,
                type = missionLabelModel.type,
                label = missionLabelModel.label,
                missionId = missionLabelModel.missionId,
                createdBy = missionLabelModel.createdBy,
                createdAt = missionLabelModel.createdAt,
                updatedBy = missionLabelModel.updatedBy,
                updatedAt = missionLabelModel.updatedAt,
            )
        }
    }
}
