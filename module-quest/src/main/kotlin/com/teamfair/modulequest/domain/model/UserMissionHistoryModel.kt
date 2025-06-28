package com.teamfair.modulequest.domain.model

import com.illsang.common.model.BaseModel
import com.teamfair.modulequest.domain.model.enums.MissionStatus
import java.time.LocalDateTime

data class UserMissionHistoryModel(
    val id: Long? = null,
    val userId: Long,
    var status: MissionStatus = MissionStatus.PENDING,
    var submissionImageUrl: String? = null,
    var submittedAt: LocalDateTime? = null,
    val missionId: Long,
    val userQuestHistoryId: Long,
    override val createdBy: String? = null,
    override val createdAt: LocalDateTime? = null,
    override val updatedBy: String? = null,
    override val updatedAt: LocalDateTime? = null
) : BaseModel(createdBy, createdAt, updatedBy, updatedAt) 