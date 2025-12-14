package com.illsang.management.repository

import com.illsang.management.domain.entity.ReportEntity
import com.illsang.management.dto.request.ReportSearchRequest
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface ReportCustomRepository {
    fun findAllBySearch(request: ReportSearchRequest, pageable: Pageable): Page<ReportEntity>
}