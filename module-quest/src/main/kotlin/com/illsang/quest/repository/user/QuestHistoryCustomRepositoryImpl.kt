package com.illsang.quest.repository.user

import com.illsang.quest.domain.entity.quest.QQuestEntity
import com.illsang.quest.domain.entity.user.QUserQuestHistoryEntity
import com.illsang.quest.enums.QuestHistoryStatus
import com.querydsl.jpa.impl.JPAQueryFactory
import java.time.LocalDateTime

class QuestHistoryCustomRepositoryImpl(
    private val queryFactory: JPAQueryFactory,
) : QuestHistoryCustomRepository {

    private val uq = QUserQuestHistoryEntity.userQuestHistoryEntity
    private val q = QQuestEntity.questEntity

    override fun findMaxConsecutiveDays(userId: String): Int {

        val dates = queryFactory
            .select(uq.completedAt)
            .from(uq)
            .where(
                uq.userId.eq(userId)
                    .and(uq.status.eq(QuestHistoryStatus.COMPLETE))
            )
            .orderBy(uq.completedAt.asc())
            .fetch()

        if (dates.isEmpty()) return 0

        var maxStreak = 1
        var currentStreak = 1
        for (i in 1 until dates.size) {
            val prev = dates[i - 1]!!.toLocalDate()
            val curr = dates[i]!!.toLocalDate()
            if (prev.plusDays(1) == curr) currentStreak++
            else if (prev != curr) currentStreak = 1

            if (currentStreak > maxStreak) maxStreak = currentStreak
        }
        return maxStreak
    }

    override fun findAllUserRankByStore(
        startDate: LocalDateTime,
        endDate: LocalDateTime,
        storeId: Long?
    ): List<String> {

        return queryFactory
            .select(uq.userId)
            .from(uq)
            .join(q).on(q.id.eq(uq.quest.id))
            .where(
                uq.completedAt.between(startDate, endDate)
                    .and(q.storeId.eq(storeId))
            )
            .groupBy(uq.userId)
            .orderBy(uq.count().desc())
            .fetch()
    }

    override fun findAllUserRankByArea(
        startDate: LocalDateTime,
        endDate: LocalDateTime,
        metroAreaCodes: List<String?>
    ): List<String> {
        return queryFactory
            .select(uq.userId)
            .from(uq)
            .join(q).on(q.id.eq(uq.quest.id))
            .where(
                uq.completedAt.between(startDate, endDate)
                    .and(q.commercialAreaCode.`in`(metroAreaCodes))
            )
            .groupBy(uq.userId)
            .orderBy(uq.count().desc())
            .fetch()
    }


}