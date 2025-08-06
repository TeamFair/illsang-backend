package com.illsang.user.repository

import com.illsang.common.enums.PointType
import com.illsang.user.domain.entity.QUserEntity.Companion.userEntity
import com.illsang.user.domain.entity.QUserPointEntity.Companion.userPointEntity
import com.illsang.user.domain.entity.UserEntity
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.core.types.dsl.StringPath
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.support.PageableExecutionUtils
import org.springframework.stereotype.Repository

@Repository
class UserPointCustomRepositoryImpl(
    val queryFactory: JPAQueryFactory,
) : UserPointCustomRepository {

    override fun findAllUserRank(
        seasonId: Long?,
        commercialAreaCode: String?,
        pointType: PointType?,
        pageable: Pageable
    ): Page<Pair<UserEntity, Long>> {
        val result = this.queryFactory
            .select(
                userEntity,
                userPointEntity.point.sumLong(),
            )
            .from(userPointEntity)
            .innerJoin(userEntity, userPointEntity.id.user).fetchJoin()
            .where(
                commercialAreaCodeEq(commercialAreaCode),
                pointTypeEq(pointType),
                seasonEq(seasonId),
            )
            .groupBy(
                userPointEntity.id.user,
            )
            .orderBy(
                userPointEntity.point.sumLong().desc()
            )
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetch()
            .map { tuple ->
                Pair(
                    tuple.get(userEntity)!!,
                    tuple.get(userPointEntity.point.sumLong())!!
                )
            }

        val count = this.queryFactory
            .select(userPointEntity.id.user.countDistinct())
            .from(userPointEntity)
            .where(
                commercialAreaCodeEq(commercialAreaCode),
                pointTypeEq(pointType),
                seasonEq(seasonId),
            )

        return PageableExecutionUtils.getPage(result, pageable) {
            count.fetchOne() ?: 0L
        }
    }

    override fun findAllAreaRank(
        seasonId: Long?,
        pointType: PointType,
        pageable: Pageable
    ): Page<Pair<String, Long>?> {
        val result = this.queryFactory
            .select(
                groupByPointType(pointType),
                userPointEntity.point.sumLong(),
            )
            .from(userPointEntity)
            .where(seasonEq(seasonId))
            .groupBy(
                groupByPointType(pointType)
            )
            .orderBy(
                userPointEntity.point.sumLong().desc()
            )
            .limit(pageable.pageSize.toLong())
            .offset(pageable.offset)
            .fetch()
            .map { tuple ->
                Pair(
                    tuple.get(groupByPointType(pointType))!!,
                    tuple.get(userPointEntity.point.sumLong())!!
                )
            }

        val count = this.queryFactory
            .select(groupByPointType(pointType).countDistinct())
            .from(userPointEntity)
            .where(seasonEq(seasonId))

        return PageableExecutionUtils.getPage(result, pageable) {
            count.fetchOne() ?: 0L
        }
    }

    private fun pointTypeEq(pointType: PointType?): BooleanExpression? {
        return pointType?.let { userPointEntity.id.pointType.eq(it) }
    }

    private fun commercialAreaCodeEq(commercialAreaCode: String?): BooleanExpression? =
        userPointEntity.id.commercialAreaCode.eq(commercialAreaCode)

    private fun seasonEq(seasonId: Long?): BooleanExpression? {
        return seasonId?.let { userPointEntity.id.seasonId.eq(it) }
    }

    private fun groupByPointType(pointType: PointType): StringPath {
        return when (pointType) {
            PointType.NONE, PointType.CONTRIBUTION -> throw IllegalAccessException("Invalid point type")
            PointType.METRO -> userPointEntity.id.metroAreaCode
            PointType.COMMERCIAL -> userPointEntity.id.commercialAreaCode
        }


    }


}
