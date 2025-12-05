package com.illsang.common.event.management.report

import com.illsang.common.enums.ReportStatusType
import com.illsang.common.enums.ReportType

data class ReportCreateEvent (
    val targetId: Long,
    val type: ReportType,
    val reason: String?,
    val userId: String,
)