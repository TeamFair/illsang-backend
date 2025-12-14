package com.illsang.common.event.user.mission

import com.illsang.common.enums.ReportStatusType

data class UserMissionGetReportStatusEvent(
    val id: Long
) {
    lateinit var response: ReportStatusType
}