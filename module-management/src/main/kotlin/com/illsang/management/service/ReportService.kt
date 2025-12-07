package com.illsang.management.service

import com.illsang.common.enums.ReportStatusType
import com.illsang.common.enums.ReportType
import com.illsang.common.event.user.mission.ChangeUserMissionCommentStatusEvent
import com.illsang.common.event.user.mission.ChangeUserMissionStatusEvent
import com.illsang.management.dto.request.ReportRequest
import com.illsang.management.repository.ReportRepository
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ReportService(
    private val reportRepository: ReportRepository,
    private val eventPublisher: ApplicationEventPublisher,
) {

    @Transactional
    fun createReport(reportRequest: ReportRequest) {
        val existingReport = this.checkExistReport(reportRequest.targetId, reportRequest.type, reportRequest.userId)

        if (existingReport) {
            throw IllegalArgumentException("이미 신고한 내역이 존재합니다.")
        }

        reportRepository.save(reportRequest.toEntity())

        val reportedCount = reportRepository.countDistinctByTargetIdAndType(
            reportRequest.targetId,
            reportRequest.type
        )

        if (reportedCount >= 3) {
            when (reportRequest.type) {
                ReportType.USER_COMMENT -> {
                    eventPublisher.publishEvent(
                        ChangeUserMissionCommentStatusEvent(
                            id = reportRequest.targetId,
                            status = ReportStatusType.REPORTED
                        )
                    )
                }
                ReportType.USER_MISSION -> {
                    eventPublisher.publishEvent(
                        ChangeUserMissionStatusEvent(
                            id = reportRequest.targetId,
                            status = ReportStatusType.REPORTED
                        )
                    )
                }
            }
        }
    }

    fun checkExistReport(targetId: Long, type: ReportType, userId: String): Boolean {
        return reportRepository.existsByTargetIdAndTypeAndUserId(targetId, type, userId)
    }
}