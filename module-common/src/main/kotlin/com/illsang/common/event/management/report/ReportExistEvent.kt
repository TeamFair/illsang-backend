package com.illsang.common.event.management.report

import com.illsang.common.enums.ReportType

data class ReportExistEvent(
    val targetId: Long,
    val type: ReportType,
    val userId: String,
){
    var response: Boolean = false
}
