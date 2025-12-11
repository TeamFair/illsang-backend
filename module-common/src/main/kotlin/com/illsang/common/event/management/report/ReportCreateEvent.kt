package com.illsang.common.event.management.report

import com.illsang.common.enums.ReportStatusType
import com.illsang.common.enums.ReportType
import com.illsang.common.enums.ResultCode

data class ReportCreateEvent (
    val targetId: Long,
    val type: ReportType,
    val reason: String?,
    val userId: String,
){
    lateinit var resultCode: ResultCode
}