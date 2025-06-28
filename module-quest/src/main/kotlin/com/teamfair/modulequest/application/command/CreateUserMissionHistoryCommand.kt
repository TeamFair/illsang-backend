package com.teamfair.modulequest.application.command

import com.teamfair.modulequest.domain.model.enums.MissionStatus
import java.time.LocalDateTime

data class CreateUserMissionHistoryCommand(
    val userId: Long,
    val status: MissionStatus = MissionStatus.PENDING,
    val submissionImageUrl: String? = null,
    val submittedAt: LocalDateTime? = null,
    val missionId: Long,
    val userQuestHistoryId: Long
) 