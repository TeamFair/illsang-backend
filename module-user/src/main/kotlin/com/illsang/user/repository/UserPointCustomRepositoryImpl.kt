package com.illsang.user.repository

import com.illsang.user.domain.entity.QUserEntity.Companion.userEntity
import com.illsang.user.domain.entity.QUserPointEntity.Companion.userPointEntity
import com.illsang.user.domain.entity.UserEntity
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.support.PageableExecutionUtils
import org.springframework.stereotype.Repository

@Repository
class UserPointCustomRepositoryImpl(
    val queryFactory: JPAQueryFactory,
) : UserPointCustomRepository {

    override fun findAllTotalRank(seasonId: Long, commercialAreaCode: String, pageable: Pageable): Page<Pair<UserEntity, Long>> {
        val result = this.queryFactory
            .select(
                userEntity,
                userPointEntity.point.sumLong(),
            )
            .from(userPointEntity)
            .innerJoin(userEntity, userPointEntity.id.user).fetchJoin()
            .where(
                userPointEntity.id.seasonId.eq(seasonId),
                userPointEntity.id.commercialAreaCode.eq(commercialAreaCode),
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
                userPointEntity.id.seasonId.eq(seasonId),
                userPointEntity.id.commercialAreaCode.eq(commercialAreaCode)
            )

        return PageableExecutionUtils.getPage(result, pageable) {
            count.fetchOne() ?: 0L
        }
    }


}
