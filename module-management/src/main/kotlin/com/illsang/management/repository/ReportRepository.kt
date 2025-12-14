package com.illsang.management.repository

import com.illsang.common.enums.ReportType
import com.illsang.management.domain.entity.ReportEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ReportRepository : JpaRepository<ReportEntity, Long>, ReportCustomRepository {

    fun existsByTargetIdAndTypeAndUserId(targetId: Long, type: ReportType, userId: String): Boolean
    fun countDistinctByTargetIdAndType(targetId: Long, type: ReportType): Long
    fun findAllByTargetIdAndType(targetId: Long, type: ReportType): List<ReportEntity>
}