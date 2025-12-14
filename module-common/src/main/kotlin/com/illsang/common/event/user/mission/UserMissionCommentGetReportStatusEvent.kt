package com.illsang.common.event.user.mission

import com.illsang.common.enums.ReportStatusType

data class UserMissionCommentGetReportStatusEvent(
    val id: Long
) {
    lateinit var response: ReportStatusType
}