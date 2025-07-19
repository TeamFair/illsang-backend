package com.illsang.management.repository

import com.illsang.management.domain.entity.BannerEntity
import com.illsang.management.domain.entity.QBannerEntity.Companion.bannerEntity
import com.illsang.management.dto.request.BannerSearchRequest
import com.querydsl.core.BooleanBuilder
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component

@Component
class BannerCustomRepositoryImpl(
    private val queryFactory: JPAQueryFactory,
) : BannerCustomRepository {

    override fun findAllBySearch(
        request: BannerSearchRequest,
        pageable: Pageable
    ): Page<BannerEntity> {

        val whereClause = BooleanBuilder()
        request.title?.let {
            whereClause.and(bannerEntity.title.containsIgnoreCase(it))
        }
        request.description?.let {
            whereClause.and(bannerEntity.description.containsIgnoreCase(it))
        }
        request.activeYn?.let {
            whereClause.and(bannerEntity.activeYn.eq(it))
        }

        val results = queryFactory
            .selectFrom(bannerEntity)
            .where(whereClause)
            .limit(pageable.pageSize.toLong())
            .offset(pageable.offset)
            .fetch()

        val total = queryFactory
            .select(bannerEntity.count())
            .from(bannerEntity)
            .where(whereClause)
            .fetchOne() ?: 0

        return PageImpl(results, pageable, total)
    }
}
