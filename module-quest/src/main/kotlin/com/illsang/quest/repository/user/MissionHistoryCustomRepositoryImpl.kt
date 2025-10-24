package com.illsang.quest.repository.user

import com.illsang.quest.domain.entity.user.QUserMissionHistoryEntity.Companion.userMissionHistoryEntity
import com.illsang.quest.domain.entity.user.UserMissionHistoryEntity
import com.illsang.quest.dto.request.user.MissionHistoryRequest
import com.illsang.quest.enums.MissionHistoryStatus
import com.querydsl.core.types.OrderSpecifier
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

@Repository
class MissionHistoryCustomRepositoryImpl(
    private val queryFactory: JPAQueryFactory,
) : MissionHistoryCustomRepository {

    override fun findSubmitUserMissionHistory(
        request: MissionHistoryRequest,
        pageable: Pageable
    ): Page<UserMissionHistoryEntity> {
        val result = this.queryFactory
            .selectFrom(userMissionHistoryEntity)
            .where(
                userMissionHistoryEntity.status.`in`(MissionHistoryStatus.APPROVED, MissionHistoryStatus.SUBMITTED),
                userMissionHistoryEntity.userId.eq(request.userId),
                request.missionType?.let { userMissionHistoryEntity.mission.type.eq(it) }
            )
            .orderBy(*orderCondition(request))
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetch()

        val count = this.queryFactory
            .select(userMissionHistoryEntity.count())
            .from(userMissionHistoryEntity)
            .where(
                userMissionHistoryEntity.status.`in`(MissionHistoryStatus.APPROVED, MissionHistoryStatus.SUBMITTED),
                userMissionHistoryEntity.userId.eq(request.userId),
                request.missionType?.let { userMissionHistoryEntity.mission.type.eq(it) }
            )
            .fetchOne() ?: 0L

        return PageImpl(result, pageable, count)
    }


    private fun orderCondition(request: MissionHistoryRequest): Array<OrderSpecifier<*>> {
        val orderSpecifiers = mutableListOf<OrderSpecifier<*>>()

        when (request.orderRewardDesc) {
            true -> {
                orderSpecifiers.add(userMissionHistoryEntity.questHistory.quest.totalPoint.desc())
            }
            false -> {
                orderSpecifiers.add(userMissionHistoryEntity.questHistory.quest.totalPoint.asc())
            }
            null ->{}
        }
        when (request.orderCreatedAtDesc){
            true ->{
                orderSpecifiers.add(userMissionHistoryEntity.createdAt.desc())
            }
            false ->{
                orderSpecifiers.add(userMissionHistoryEntity.createdAt.asc())
            }
            null ->{
                orderSpecifiers.add(userMissionHistoryEntity.createdAt.desc())
            }
        }

        return orderSpecifiers.toTypedArray()
    }
}