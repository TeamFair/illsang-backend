package com.illsang.common.event.user.mission

import com.illsang.common.enums.ReportStatusType

data class ChangeUserMissionStatusEvent(
    val id: Long,
    val status: ReportStatusType,
)