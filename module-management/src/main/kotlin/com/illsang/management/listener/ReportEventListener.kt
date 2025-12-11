package com.illsang.management.listener

import com.illsang.common.event.management.report.ReportCreateEvent
import com.illsang.common.event.management.report.ReportExistEvent
import com.illsang.management.dto.request.ReportRequest
import com.illsang.management.service.ReportService
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class ReportEventListener(
    private val reportService: ReportService
) {

    @EventListener
    fun createReport(event: ReportCreateEvent){
        val request = ReportRequest(
            type = event.type,
            reason = event.reason,
            targetId = event.targetId,
            userId = event.userId,
        )
        val reportResponse = this.reportService.createReport(request)
        event.resultCode = reportResponse

    }

    @EventListener
    fun checkExistReport(event: ReportExistEvent){
        event.response = this.reportService.checkExistReport(event.targetId, event.type, event.userId)
    }
}