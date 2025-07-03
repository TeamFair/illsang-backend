package com.illsang.modulequest.adapter.`in`.web.model.request

import com.illsang.modulequest.domain.model.enums.MissionStatus
import java.time.LocalDateTime

data class UpdateUserMissionHistoryRequest(
    val status: MissionStatus? = null,
    val submissionImageUrl: String? = null,
    val submittedAt: LocalDateTime? = null
)
