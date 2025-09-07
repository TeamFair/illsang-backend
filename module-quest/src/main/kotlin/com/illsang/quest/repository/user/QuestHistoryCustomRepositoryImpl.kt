package com.illsang.quest.repository.user

import com.illsang.quest.domain.entity.quest.QQuestEntity
import com.illsang.quest.domain.entity.user.QUserQuestHistoryEntity
import com.illsang.quest.enums.QuestHistoryStatus
import com.querydsl.core.BooleanBuilder
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

        val builder = BooleanBuilder()
            .and(uq.completedAt.between(startDate, endDate))

        // storeId가 null이면 필터를 적용하지 않음(전체 매장)
        if (storeId != null) {
            builder.and(q.storeId.eq(storeId))
        }

        return queryFactory
            .select(uq.userId)
            .from(uq)
            .join(q).on(q.id.eq(uq.quest.id))
            .where(builder)
            .groupBy(uq.userId)
            .orderBy(uq.count().desc())
            .fetch()
    }

    override fun findAllUserRankByArea(
        startDate: LocalDateTime,
        endDate: LocalDateTime,
        metroAreaCodes: List<String?>
    ): List<String> {
        val builder = BooleanBuilder()
            .and(uq.completedAt.between(startDate, endDate))

        // null 제거 + 공백/빈 문자열 제거(Optional)
        val codes = metroAreaCodes
            .filterNotNull()
            .map { it.trim() }
            .filter { it.isNotEmpty() }
            .distinct()

        // 리스트가 비었거나(null 포함 등) "전체"로 간주되면 지역 필터 미적용
        if (codes.isNotEmpty()) {
            builder.and(q.commercialAreaCode.`in`(codes))
        }

        return queryFactory
            .select(uq.userId)
            .from(uq)
            .join(q).on(q.id.eq(uq.quest.id))
            .where(builder)
            .groupBy(uq.userId)
            .orderBy(uq.count().desc())
            .fetch()
    }


}