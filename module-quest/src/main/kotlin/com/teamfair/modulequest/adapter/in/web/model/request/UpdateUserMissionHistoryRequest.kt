package com.teamfair.modulequest.adapter.`in`.web.model.request

import com.teamfair.modulequest.domain.model.enums.MissionStatus
import java.time.LocalDateTime

data class UpdateUserMissionHistoryRequest(
    val status: MissionStatus? = null,
    val submissionImageUrl: String? = null,
    val submittedAt: LocalDateTime? = null
) 