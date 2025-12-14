package com.illsang.management.service

import com.illsang.common.enums.ReportStatusType
import com.illsang.common.enums.ReportType
import com.illsang.common.enums.ResultCode
import com.illsang.common.event.user.mission.ChangeUserMissionCommentStatusEvent
import com.illsang.common.event.user.mission.ChangeUserMissionStatusEvent
import com.illsang.common.event.user.mission.UserMissionCommentGetReportStatusEvent
import com.illsang.common.event.user.mission.UserMissionGetReportStatusEvent
import com.illsang.management.domain.entity.ReportEntity
import com.illsang.management.dto.request.ReportDetailRequest
import com.illsang.management.dto.request.ReportRequest
import com.illsang.management.dto.request.ReportSearchRequest
import com.illsang.management.dto.request.ReportUpdateRequest
import com.illsang.management.dto.response.ReportDetailResponse
import com.illsang.management.dto.response.ReportResponse
import com.illsang.management.repository.ReportRepository
import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ReportService(
    private val reportRepository: ReportRepository,
    private val eventPublisher: ApplicationEventPublisher,
) {

    @Transactional
    fun createReport(reportRequest: ReportRequest): ResultCode {
        val existingReport = this.checkExistReport(reportRequest.targetId, reportRequest.type, reportRequest.userId)

        if (existingReport) {
            return ResultCode.REPORT_DUPLICATE
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
        return ResultCode.SUCCESS
    }

    fun checkExistReport(targetId: Long, type: ReportType, userId: String): Boolean {
        return reportRepository.existsByTargetIdAndTypeAndUserId(targetId, type, userId)
    }

    fun search(request: ReportSearchRequest, pageable: Pageable): Page<ReportResponse> {
        val reports = this.reportRepository.findAllBySearch(request, pageable)

        return reports.map { report -> ReportResponse.from(report) }
    }

    fun getReport(targetId: Long, type: ReportType): ReportDetailResponse {
        val reports = this.reportRepository.findAllByTargetIdAndType(targetId, type)
        val report = reports.firstOrNull()
        val status = report?.let {
            when (it.type) {
                ReportType.USER_COMMENT -> {
                    val event = UserMissionCommentGetReportStatusEvent(report.targetId)
                    eventPublisher.publishEvent(event)
                    event.response
                }
                ReportType.USER_MISSION -> {
                    val event = UserMissionGetReportStatusEvent(report.targetId)
                    eventPublisher.publishEvent(event)
                    event.response
                }
            }
        }

        return ReportDetailResponse.from(reports, status!!)
    }

    @Transactional
    fun changeReportStatus(request: ReportUpdateRequest){

        when (request.type) {
            ReportType.USER_COMMENT -> {
                eventPublisher.publishEvent(
                    ChangeUserMissionCommentStatusEvent(
                        id = request.targetId,
                        status = request.status
                    )
                )
            }
            ReportType.USER_MISSION -> {
                eventPublisher.publishEvent(
                    ChangeUserMissionStatusEvent(
                        id = request.targetId,
                        status = request.status
                    )
                )
            }
        }
    }
    
    private fun findById(id: Long): ReportEntity = reportRepository.findById(id)
        .orElseThrow { IllegalArgumentException("Report not found with id: $id") }
}