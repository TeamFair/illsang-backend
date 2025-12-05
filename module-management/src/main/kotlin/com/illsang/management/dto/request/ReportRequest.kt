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