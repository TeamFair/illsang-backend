package com.illsang.quest.repository.quest

import com.illsang.quest.domain.entity.history.QUserQuestHistoryEntity.Companion.userQuestHistoryEntity
import com.illsang.quest.domain.entity.quest.QQuestEntity.Companion.questEntity
import com.illsang.quest.domain.entity.quest.QQuestRewardEntity.Companion.questRewardEntity
import com.illsang.quest.domain.entity.quest.QuestEntity
import com.illsang.quest.dto.request.quest.QuestUserRequest
import com.illsang.quest.enums.QuestRepeatFrequency
import com.illsang.quest.enums.QuestType
import com.querydsl.core.types.ExpressionUtils
import com.querydsl.core.types.OrderSpecifier
import com.querydsl.core.types.Predicate
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.support.PageableExecutionUtils
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

@Component
class QuestUserCustomRepositoryImpl(
    private val queryFactory: JPAQueryFactory,
) : QuestUserCustomRepository {

    override fun findAllUncompletedQuest(
        request: QuestUserRequest,
        pageable: Pageable,
    ): Page<QuestEntity> {
        val result = this.queryFactory
            .selectFrom(questEntity)
            .leftJoin(questEntity.rewards, questRewardEntity).fetchJoin()
            .leftJoin(questEntity, userQuestHistoryEntity.quest).fetchJoin()
            .on(
                userQuestHistoryEntity.userId.eq(request.userId),
                ExpressionUtils.or(
                    questEntity.type.ne(QuestType.REPEAT),
                    repeatQuestCondition(LocalDateTime.now())
                )
            )
            .where(
                questEntity.useYn.isTrue,
                userQuestHistoryEntity.isNull,
                popularYnEq(request.popularYn),
                questEntity.expireDate.gt(LocalDateTime.now()),
            )
            .orderBy(
                *orderCondition(request)
            )
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetch()

        val count = this.queryFactory
            .select(questEntity.id.countDistinct())
            .from(questEntity)
            .leftJoin(questEntity.rewards, questRewardEntity).fetchJoin()
            .leftJoin(questEntity, userQuestHistoryEntity.quest).fetchJoin()
            .on(
                userQuestHistoryEntity.userId.eq(request.userId),
                ExpressionUtils.or(
                    questEntity.type.ne(QuestType.REPEAT),
                    repeatQuestCondition(LocalDateTime.now())
                )
            )
            .where(
                questEntity.useYn.isTrue,
                userQuestHistoryEntity.isNull,
                questEntity.expireDate.gt(LocalDateTime.now()),
                popularYnEq(request.popularYn),
                commercialAreaCodeEq(request.commercialAreaCode),
            )

        return PageableExecutionUtils.getPage(result, pageable) {
            count.fetchOne() ?: 0L
        }
    }

    private fun orderCondition(request: QuestUserRequest): Array<OrderSpecifier<*>> {
        return if (request.orderReward) {
            arrayOf(
                questEntity.totalPoint.desc()
            )
        } else {
            arrayOf(
                questEntity.sortOrder.desc(),
                questEntity.createdAt.asc()
            )
        }
    }

    private fun repeatQuestCondition(today: LocalDateTime): Predicate? {
        val dailyCondition = questEntity.repeatFrequency.eq(QuestRepeatFrequency.DAILY)
            .and(userQuestHistoryEntity.createdAt.goe(today.truncatedTo(ChronoUnit.DAYS)))
        val weeklyCondition = questEntity.repeatFrequency.eq(QuestRepeatFrequency.WEEKLY)
            .and(userQuestHistoryEntity.createdAt.goe(today.minusDays(6).truncatedTo(ChronoUnit.DAYS)))
        val monthlyCondition = questEntity.repeatFrequency.eq(QuestRepeatFrequency.MONTHLY)
            .and(userQuestHistoryEntity.createdAt.goe(today.minusDays(29).truncatedTo(ChronoUnit.DAYS)))

        val targetTimeCondition = ExpressionUtils.or(
            dailyCondition,
            ExpressionUtils.or(weeklyCondition, monthlyCondition)
        )


        return ExpressionUtils.and(
            questEntity.type.eq(QuestType.REPEAT),
            targetTimeCondition
        )
    }

    private fun popularYnEq(popularYn: Boolean?): BooleanExpression? {
        return popularYn?.let { questEntity.popularYn.eq(it) }
    }

    private fun commercialAreaCodeEq(commercialAreaCode: String?): BooleanExpression? {
        return commercialAreaCode?.let { questEntity.commercialAreaCode.eq(it) }
    }

}
