package com.illsang.modulequest.adapter.`in`.web.model.request

import com.illsang.modulequest.domain.model.enums.MissionStatus
import java.time.LocalDateTime

data class CreateUserMissionHistoryRequest(
    val userId: Long,
    val status: MissionStatus = MissionStatus.PENDING,
    val submissionImageUrl: String? = null,
    val submittedAt: LocalDateTime? = null,
    val missionId: Long,
    val userQuestHistoryId: Long
)
