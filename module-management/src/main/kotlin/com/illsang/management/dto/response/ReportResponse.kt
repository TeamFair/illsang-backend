package com.illsang.management.dto.response

import com.illsang.common.enums.ReportStatusType
import com.illsang.common.enums.ReportType
import com.illsang.management.domain.entity.ReportEntity

data class ReportResponse(
    val id: Long?,
    val type: ReportType,
    val targetId: Long,
    val reason: String?,
    val userId: String,

) {
    companion object {
        fun from(report: ReportEntity): ReportResponse {
            return ReportResponse(
                id = report.id,
                type = report.type,
                targetId = report.targetId,
                reason = report.reason,
                userId = report.userId,
            )
        }

    }
}

data class ReportDetailResponse(
    val reports: List<ReportResponse>,
    val status: ReportStatusType,
) {
    companion object {
        fun from(
            reports: List<ReportEntity>,
            status: ReportStatusType
        ) = ReportDetailResponse(
            reports = reports.map(ReportResponse::from),
            status = status
        )
    }
}