package com.illsang.quest.repository.quest

import com.illsang.quest.domain.entity.quest.QMissionEntity.Companion.missionEntity
import com.illsang.quest.domain.entity.quest.QQuestEntity.Companion.questEntity
import com.illsang.quest.domain.entity.quest.QuestEntity
import com.illsang.quest.dto.request.quest.QuestGetListRequest
import com.illsang.quest.enums.MissionType
import com.illsang.quest.enums.QuestRepeatFrequency
import com.illsang.quest.enums.QuestType
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.JPAExpressions
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.support.PageableExecutionUtils
import org.springframework.stereotype.Repository

@Repository
class QuestCustomRepositoryImpl(
    private val queryFactory: JPAQueryFactory,
) : QuestCustomRepository {

    override fun findByRequest(
        request: QuestGetListRequest,
        pageable: Pageable,
    ): Page<QuestEntity> {
        val result = this.queryFactory
            .select(questEntity)
            .from(questEntity)
            .where(
                questTypeEq(request.type, request.repeatFrequency),
                commercialAreaCodeEq(request.commercialAreaCodes),
                missionTypeEq(request.missionType),
            )
            .orderBy(
                questEntity.updatedAt.desc()
            )
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetch()

        val countQuery = this.queryFactory
            .select(questEntity.id.countDistinct())
            .from(questEntity)
            .where(
                questTypeEq(request.type, request.repeatFrequency),
                commercialAreaCodeEq(request.commercialAreaCodes),
                missionTypeEq(request.missionType),
            )

        return PageableExecutionUtils.getPage(result, pageable) { countQuery.fetchOne() ?: 0L }
    }

    private fun questTypeEq(questType: QuestType?, repeatFrequency: QuestRepeatFrequency?): BooleanExpression? {
        if (questType == null) {
            return null
        }

        if (repeatFrequency == null) {
            return questEntity.type.eq(questType)
        }

        return questEntity.type.eq(questType).and(questEntity.repeatFrequency.eq(repeatFrequency))
    }

    private fun commercialAreaCodeEq(commercialAreaCodes: MutableList<String>?): BooleanExpression? {
        return commercialAreaCodes?.let { questEntity.commercialAreaCode.`in`(it) }
    }

    private fun missionTypeEq(missionType: MissionType?): BooleanExpression? {
        return missionType?.let {
            JPAExpressions.selectOne()
                .from(missionEntity)
                .where(
                    missionEntity.quest.eq(questEntity),
                    missionEntity.type.eq(it)
                ).exists()
        }
    }
}
