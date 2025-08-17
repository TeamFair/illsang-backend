package com.illsang.user.repository

import com.illsang.common.enums.PointType
import com.illsang.user.domain.entity.QUserEntity.Companion.userEntity
import com.illsang.user.domain.entity.QUserPointEntity.Companion.userPointEntity
import com.illsang.user.domain.model.UserRankModel
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
        metroCode: String?,
        commercialAreaCode: String?,
        pointType: PointType?,
        pageable: Pageable
    ): Page<UserRankModel> {
        val result = this.queryFactory
            .select(
                userEntity,
                userPointEntity.point.sumLong(),
            )
            .from(userPointEntity)
            .innerJoin(userEntity, userPointEntity.id.user).fetchJoin()
            .where(
                metroCodeEq(metroCode),
                commercialAreaCodeEq(commercialAreaCode),
                pointTypeEq(pointType),
                seasonEq(seasonId),
            )
            .groupBy(
                userPointEntity.id.user,
            )
            .orderBy(
                userPointEntity.point.sumLong().desc(),
                userPointEntity.createdAt.max().asc(),
            )
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetch()
            .mapIndexed { index, tuple ->
                UserRankModel.from(
                    user = tuple.get(userEntity)!!,
                    point = tuple.get(userPointEntity.point.sumLong())!!,
                    rank = pageable.offset + index + 1L,
                )
            }

        val count = this.queryFactory
            .select(userPointEntity.id.user.countDistinct())
            .from(userPointEntity)
            .where(
                metroCodeEq(metroCode),
                commercialAreaCodeEq(commercialAreaCode),
                pointTypeEq(pointType),
                seasonEq(seasonId),
            )

        return PageableExecutionUtils.getPage(result, pageable) {
            count.fetchOne() ?: 0L
        }
    }

    override fun findUserRankPosition(
        userId: String,
        seasonId: Long?,
        areaCode: String?,
        pointType: PointType,
    ): UserRankModel {
        val targetUserData = queryFactory
            .select(
                userEntity,
                userPointEntity.point.sumLong().coalesce(0L),
                userPointEntity.createdAt.max(),
            )
            .from(userEntity)
            .innerJoin(userEntity, userPointEntity.id.user).fetchJoin()
            .where(
                userEntity.id.eq(userId),
                areaCodeEq(areaCode, pointType),
                seasonEq(seasonId)
            )
            .groupBy(userEntity.id)
            .fetchOne()

        if (targetUserData!!.get(userPointEntity.point.sumLong()) == 0L) {
            return UserRankModel.from(
                user = targetUserData.get(userEntity)!!,
            )
        }

        val targetPoints = targetUserData.get(userPointEntity.point.sumLong())!!
        val targetCreatedAt = targetUserData.get(userPointEntity.createdAt.max())!!

        val higherRankedCount = queryFactory
            .select(userEntity.id.countDistinct())
            .from(userPointEntity)
            .innerJoin(userEntity, userPointEntity.id.user)
            .where(
                areaCodeEq(areaCode, pointType),
                pointTypeEq(pointType),
                seasonEq(seasonId)
            )
            .groupBy(userEntity.id)
            .having(
                userPointEntity.point.sumLong().gt(targetPoints)
                    .or(
                        userPointEntity.point.sumLong().eq(targetPoints)
                            .and(userPointEntity.createdAt.max().lt(targetCreatedAt))
                    )
            )
            .fetchOne() ?: 0

        return UserRankModel.from(
            user = targetUserData.get(userEntity)!!,
            point = targetPoints,
            rank = higherRankedCount + 1L,
        )
    }

    override fun findAllAreaRank(
        seasonId: Long?,
        pointType: PointType,
        pageable: Pageable,
    ): Page<Pair<String, Long>> {
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
                userPointEntity.point.sumLong().desc(),
                userPointEntity.createdAt.max().asc(),
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

    override fun findOwnerPoint(
        pointType: PointType,
        userId: String,
    ): List<Pair<String, Long>> {
        return this.queryFactory
            .select(
                groupByPointType(pointType),
                userPointEntity.point.sumLong().coalesce(0L),
            )
            .from(userPointEntity)
            .where(
                userPointEntity.id.user.id.eq(userId),
                pointTypeEq(pointType),
            )
            .groupBy(
                groupByPointType(pointType),
            )
            .orderBy(
                userPointEntity.point.sumLong().desc(),
            )
            .fetch()
            .map { tuple ->
                Pair(
                    tuple.get(groupByPointType(pointType))!!,
                    tuple.get(userPointEntity.point.sumLong())!!,
                )
            }
    }

    override fun findOwnerPointStatistic(userId: String, seasonId: Long?): List<Pair<PointType, Long>> {
        return this.queryFactory
            .select(
                userPointEntity.id.pointType,
                userPointEntity.point.sumLong().coalesce(0L),
            )
            .from(userPointEntity)
            .where(
                seasonEq(seasonId),
            )
            .groupBy(
                userPointEntity.id.pointType,
            )
            .fetch()
            .map { tuple ->
                Pair(
                    tuple.get(userPointEntity.id.pointType)!!,
                    tuple.get(userPointEntity.point.sumLong())!!,
                )
            }
    }

    private fun areaCodeEq(areaCode: String?, pointType: PointType): BooleanExpression? {
        return when (pointType) {
            PointType.METRO ->
                userPointEntity.id.metroAreaCode.eq(areaCode).and(userPointEntity.id.pointType.eq(pointType))

            PointType.COMMERCIAL ->
                userPointEntity.id.commercialAreaCode.eq(areaCode).and(userPointEntity.id.pointType.eq(pointType))

            PointType.CONTRIBUTION ->
                userPointEntity.id.pointType.eq(pointType)

            else -> throw IllegalAccessException("Invalid point type")
        }
    }

    private fun metroCodeEq(metroCode: String?): BooleanExpression? {
        return metroCode?.let { userPointEntity.id.metroAreaCode.eq(it) }
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
