package com.illsang.quest.repository.quest

import com.illsang.quest.domain.entity.quest.QQuestEntity.Companion.questEntity
import com.illsang.quest.domain.entity.quest.QQuestRewardEntity.Companion.questRewardEntity
import com.illsang.quest.domain.entity.quest.QuestEntity
import com.illsang.quest.domain.entity.user.QUserQuestFavoriteEntity.Companion.userQuestFavoriteEntity
import com.illsang.quest.domain.entity.user.QUserQuestHistoryEntity.Companion.userQuestHistoryEntity
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
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

@Repository
class QuestUserCustomRepositoryImpl(
    private val queryFactory: JPAQueryFactory,
) : QuestUserCustomRepository {

    override fun findAllUserQuest(
        request: QuestUserRequest,
        pageable: Pageable,
    ): Page<QuestEntity> {
        val contentIds = this.queryFactory
            .select(questEntity.id)
            .from(questEntity)
            .leftJoin(questEntity.rewards, questRewardEntity)
            .leftJoin(userQuestHistoryEntity)
            .on(
                userQuestHistoryEntity.quest.eq(questEntity),
                userQuestHistoryEntity.userId.eq(request.userId),
                ExpressionUtils.or(
                    questEntity.type.ne(QuestType.REPEAT),
                    repeatQuestCondition(LocalDateTime.now())
                )
            )
            .leftJoin(userQuestFavoriteEntity).on(
                questEntity.id.eq(userQuestFavoriteEntity.questId),
                userQuestFavoriteEntity.userId.eq(request.userId),
            )
            .where(
                questEntity.useYn.isTrue,
                questEntity.expireDate.gt(LocalDateTime.now()),
                questTypeEq(request.questType, request.repeatFrequency),
                completedYnEq(request.completedYn),
                popularYnEq(request.popularYn),
                favoriteYnEq(request.favoriteYn),
                commercialAreaCodeEq(request.commercialAreaCode),
                bannerEq(request.bannerId),
            )
            .orderBy(
                *orderCondition(request)
            )
            .groupBy(questEntity.id)
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetch()

        if (contentIds.isEmpty()) {
            return Page.empty(pageable)
        }

        val result = this.queryFactory
            .selectFrom(questEntity)
            .leftJoin(questEntity.rewards, questRewardEntity).fetchJoin()
            .leftJoin(userQuestHistoryEntity)
            .on(
                userQuestHistoryEntity.quest.eq(questEntity),
                userQuestHistoryEntity.userId.eq(request.userId),
                ExpressionUtils.or(
                    questEntity.type.ne(QuestType.REPEAT),
                    repeatQuestCondition(LocalDateTime.now())
                )
            ).fetchJoin()
            .leftJoin(userQuestFavoriteEntity).on(
                questEntity.id.eq(userQuestFavoriteEntity.questId),
                userQuestFavoriteEntity.userId.eq(request.userId),
            )
            .where(questEntity.id.`in`(contentIds))
            .orderBy(*orderCondition(request))
            .fetch()

        val countQuery = this.queryFactory
            .select(questEntity.id.countDistinct())
            .from(questEntity)
            .leftJoin(questEntity.rewards, questRewardEntity)
            .leftJoin(userQuestHistoryEntity)
            .on(
                userQuestHistoryEntity.quest.eq(questEntity),
                userQuestHistoryEntity.userId.eq(request.userId),
                ExpressionUtils.or(
                    questEntity.type.ne(QuestType.REPEAT),
                    repeatQuestCondition(LocalDateTime.now())
                )
            )
            .leftJoin(userQuestFavoriteEntity).on(
                questEntity.id.eq(userQuestFavoriteEntity.questId),
                userQuestFavoriteEntity.userId.eq(request.userId),
            )
            .where(
                questEntity.useYn.isTrue,
                questEntity.expireDate.gt(LocalDateTime.now()),
                questTypeEq(request.questType, request.repeatFrequency),
                completedYnEq(request.completedYn),
                popularYnEq(request.popularYn),
                favoriteYnEq(request.favoriteYn),
                commercialAreaCodeEq(request.commercialAreaCode),
                bannerEq(request.bannerId),
            )

        return PageableExecutionUtils.getPage(result, pageable) { countQuery.fetchOne() ?: 0L }
    }

    private fun bannerEq(bannerId: Long?): BooleanExpression? {
        if (bannerId == null) { return null }

        return questEntity.bannerId.eq(bannerId)
    }

    private fun favoriteYnEq(favoriteYn: Boolean?): BooleanExpression? {
        if (favoriteYn == null) {
            return null
        }

        if (favoriteYn) {
            return userQuestFavoriteEntity.id.isNotNull
        }

        return userQuestFavoriteEntity.id.isNull
    }

    private fun questTypeEq(questType: QuestType?, repeatFrequency: QuestRepeatFrequency?): BooleanExpression? {
        if (questType == null) {
            return null
        }

        if(repeatFrequency == null){
            return questEntity.type.eq(questType)
        }

        return questEntity.type.eq(questType).and(questEntity.repeatFrequency.eq(repeatFrequency))
    }

    private fun completedYnEq(completedYn: Boolean): BooleanExpression {
        if (completedYn) {
            return userQuestHistoryEntity.id.isNotNull
        }

        return userQuestHistoryEntity.id.isNull
    }

    private fun orderCondition(request: QuestUserRequest): Array<OrderSpecifier<*>> {
        val orderSpecifiers = mutableListOf<OrderSpecifier<*>>()

        request.orderRewardDesc?.let {
            orderSpecifiers.add(if (it) questEntity.totalPoint.desc() else questEntity.totalPoint.asc())
        }

        request.orderExpiredDesc?.let {
            orderSpecifiers.add(if (it) questEntity.expireDate.desc() else questEntity.expireDate.asc())
        }

        orderSpecifiers.addAll(
            listOf(
                questEntity.sortOrder.desc(),
                questEntity.createdAt.asc(),
            )
        )

        return orderSpecifiers.toTypedArray()
    }

    private fun repeatQuestCondition(today: LocalDateTime): Predicate? {
        val dailyCondition = questEntity.repeatFrequency.eq(QuestRepeatFrequency.DAILY)
            .and(userQuestHistoryEntity.completedAt.goe(today.truncatedTo(ChronoUnit.DAYS)))
        val weeklyCondition = questEntity.repeatFrequency.eq(QuestRepeatFrequency.WEEKLY)
            .and(userQuestHistoryEntity.completedAt.goe(today.minusDays(6).truncatedTo(ChronoUnit.DAYS)))
        val monthlyCondition = questEntity.repeatFrequency.eq(QuestRepeatFrequency.MONTHLY)
            .and(userQuestHistoryEntity.completedAt.goe(today.minusDays(29).truncatedTo(ChronoUnit.DAYS)))

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
