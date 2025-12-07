package com.illsang.quest.domain.model.quset

import com.illsang.common.domain.model.BaseModel
import com.illsang.quest.domain.entity.quest.MissionLabelEntity
import com.illsang.quest.enums.MissionLabelType
import java.time.LocalDateTime

data class MissionLabelModel(
    val id: Long? = null,
    var type: MissionLabelType,
    var label: String,
    val missionId: Long,
    override val createdBy: String? = null,
    override val createdAt: LocalDateTime? = null,
    override val updatedBy: String? = null,
    override val updatedAt: LocalDateTime? = null
) : BaseModel(createdBy, createdAt, updatedBy, updatedAt) {
    companion object {
        fun from(missionLabel: MissionLabelEntity): MissionLabelModel {
            return MissionLabelModel(
                id = missionLabel.id,
                type = missionLabel.type,
                label = missionLabel.label,
                missionId = missionLabel.mission.id!!,
                createdBy = missionLabel.createdBy,
                createdAt = missionLabel.createdAt,
                updatedBy = missionLabel.updatedBy,
                updatedAt = missionLabel.updatedAt,
            )
        }
    }
}
