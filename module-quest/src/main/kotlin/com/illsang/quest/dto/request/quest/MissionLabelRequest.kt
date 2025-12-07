package com.illsang.quest.dto.request.quest

import com.illsang.quest.domain.entity.quest.MissionEntity
import com.illsang.quest.domain.entity.quest.MissionLabelEntity
import com.illsang.quest.enums.MissionLabelType

data class MissionLabelCreateRequest(
    val missionId: Long,
    val type: MissionLabelType,
    val label: String,
) {

    fun toEntity(mission: MissionEntity): MissionLabelEntity {
        val missionLabel = MissionLabelEntity(
            type = this.type,
            label = this.label,
            mission = mission,
        )

        return missionLabel
    }

}

data class MissionLabelCreateWithMissionRequest(
    val type: MissionLabelType,
    val label: String,
) {
    fun toEntity(mission: MissionEntity): MissionLabelEntity {
        val missionLabel = MissionLabelEntity(
            type = this.type,
            label = this.label,
            mission = mission,
        )

        return missionLabel
    }
}

data class MissionLabelUpdateRequest(
    val type: MissionLabelType,
    val label: String,
)
