package com.illsang.modulequest.application.command

import com.illsang.modulequest.domain.model.enums.MissionStatus
import java.time.LocalDateTime

data class UpdateUserMissionHistoryCommand(
    val id: Long,
    val status: MissionStatus? = null,
    val submissionImageUrl: String? = null,
    val submittedAt: LocalDateTime? = null
)
