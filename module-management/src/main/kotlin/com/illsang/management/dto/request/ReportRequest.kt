package com.illsang.management.dto.request

import com.illsang.common.enums.ReportStatusType
import com.illsang.common.enums.ReportType
import com.illsang.management.domain.entity.ReportEntity

data class ReportRequest(
    val type: ReportType,
    val targetId: Long,
    val reason: String?,
    val userId: String,
) {
    fun toEntity(): ReportEntity {
        return ReportEntity(
            type = type,
            reason = reason,
            targetId = targetId,
            userId = userId,
        )
    }
}

data class ReportSearchRequest(
    val type: ReportType? = null,
    val userId: String? = null,
)

data class ReportUpdateRequest(
    val status: ReportStatusType,
    val targetId: Long,
    val type: ReportType,
)

data class ReportDetailRequest(
    val targetId: Long,
    val type: ReportType,
)