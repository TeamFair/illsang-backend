package com.illsang.management.repository

import com.illsang.management.domain.entity.QReportEntity.Companion.reportEntity
import com.illsang.management.domain.entity.ReportEntity
import com.illsang.management.dto.request.ReportSearchRequest
import com.querydsl.core.BooleanBuilder
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

@Repository
class ReportCustomRepositoryImpl(
    private val queryFactory: JPAQueryFactory,
) : ReportCustomRepository {

    override fun findAllBySearch(
        request: ReportSearchRequest,
        pageable: Pageable
    ): Page<ReportEntity> {
        val whereClause = BooleanBuilder()
        request.type?.let{
            whereClause.and(reportEntity.type.eq(it))
        }
        request.userId?.let{
            whereClause.and(reportEntity.userId.eq(it))
        }

        val results = queryFactory
            .selectFrom(reportEntity)
            .where(whereClause)
            .limit(pageable.pageSize.toLong())
            .offset(pageable.offset)
            .fetch()

        val total = queryFactory
            .select(reportEntity.count())
            .from(reportEntity)
            .where(whereClause)
            .fetchOne() ?: 0

        return PageImpl(results, pageable, total)
    }
}