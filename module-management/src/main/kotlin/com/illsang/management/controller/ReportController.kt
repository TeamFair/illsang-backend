package com.illsang.management.controller

import com.illsang.common.enums.ReportType
import com.illsang.management.dto.request.ReportDetailRequest
import com.illsang.management.dto.request.ReportSearchRequest
import com.illsang.management.dto.request.ReportUpdateRequest
import com.illsang.management.dto.response.ReportDetailResponse
import com.illsang.management.dto.response.ReportResponse
import com.illsang.management.service.ReportService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springdoc.core.annotations.ParameterObject
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/report")
@Tag(name = "Report", description = "신고하기")
class ReportController(
    private val reportService: ReportService
) {

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    @Operation(operationId = "REP001", summary = "신고하기 전체 이력 조회")
    fun getAll(
        @ParameterObject @PageableDefault(size = 10) pageable: Pageable,
        @ParameterObject request: ReportSearchRequest
    ): ResponseEntity<Page<ReportResponse>> {
        val reports = reportService.search(request, pageable)
        return ResponseEntity.ok(reports)
    }

    @GetMapping("/targetId/{targetId}/type/{type}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @Operation(operationId = "REP002", summary = "신고 상태 조회")
    fun getDetail(
        @PathVariable targetId: Long,
        @PathVariable type: ReportType
    ): ResponseEntity<ReportDetailResponse> {
        val reports = reportService.getReport(targetId, type)
        return ResponseEntity.ok(reports)
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    @Operation(operationId = "REP003", summary = "신고상태 변경")
    fun report(
        @RequestBody request: ReportUpdateRequest
    ): ResponseEntity<Void> {

        reportService.changeReportStatus(request)
        return ResponseEntity.ok().build()
    }
}