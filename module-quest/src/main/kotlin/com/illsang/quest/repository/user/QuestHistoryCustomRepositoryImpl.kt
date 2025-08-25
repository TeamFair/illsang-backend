package com.illsang.quest.repository.user

import com.illsang.quest.domain.entity.user.QUserQuestHistoryEntity
import com.illsang.quest.enums.QuestHistoryStatus
import com.querydsl.jpa.impl.JPAQueryFactory

class QuestHistoryCustomRepositoryImpl(
    private val queryFactory: JPAQueryFactory,
) : QuestHistoryCustomRepository {
    override fun findMaxConsecutiveDays(userId: String): Int {
        val uq = QUserQuestHistoryEntity.userQuestHistoryEntity

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
}