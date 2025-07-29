package com.illsang.quest.domain.model

import com.illsang.common.domain.model.BaseModel
import com.illsang.quest.domain.entity.MissionEntity
import com.illsang.quest.enums.MissionType
import java.time.LocalDateTime

data class MissionModel(
    val id: Long? = null,
    var type: MissionType,
    var sortOrder: Int?,
    val questId: Long,
    override val createdBy: String? = null,
    override val createdAt: LocalDateTime? = null,
    override val updatedBy: String? = null,
    override val updatedAt: LocalDateTime? = null
) : BaseModel(createdBy, createdAt, updatedBy, updatedAt) {

    companion object {
        fun from(mission: MissionEntity): MissionModel {
            return MissionModel(
                id = mission.id,
                type = mission.type,
                sortOrder = mission.sortOrder,
                questId = mission.quest.id!!,
                createdBy = mission.createdBy,
                createdAt = mission.createdAt,
                updatedBy = mission.updatedBy,
                updatedAt = mission.updatedAt,
            )
        }
    }

}
