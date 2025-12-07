package com.illsang.quest.service.user

import com.illsang.common.enums.CouponType
import com.illsang.common.event.management.area.CommercialAreaByMetroAreaGetEvent
import com.illsang.common.event.management.area.CommercialAreaGetEvent
import com.illsang.common.event.management.area.MetroAreaGetByCommercialAreaEvent
import com.illsang.common.event.management.area.MetroAreaGetEvent
import com.illsang.common.event.management.season.SeasonGetCurrentEvent
import com.illsang.common.event.management.store.StoreInfoGetEvent
import com.illsang.common.event.user.point.UserPointCreateEvent
import com.illsang.common.event.user.point.UserPointCreateRequest
import com.illsang.common.event.user.title.UserTitleQuestCompleteEvent
import com.illsang.quest.domain.entity.quest.QuestEntity
import com.illsang.quest.domain.entity.user.UserQuestHistoryEntity
import com.illsang.quest.enums.QuestHistoryStatus
import com.illsang.quest.enums.RewardType
import com.illsang.quest.repository.user.QuestHistoryCustomRepository
import com.illsang.quest.repository.user.QuestHistoryRepository
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.time.ZoneId

@Service
@Transactional(readOnly = true)
class QuestHistoryService(
    private val userQuestHistoryRepository: QuestHistoryRepository,
    private val eventPublisher: ApplicationEventPublisher,
) {

    @Transactional
    fun findOrCreate(userId: String, quest: QuestEntity): UserQuestHistoryEntity {
        val currentSeasonEvent = SeasonGetCurrentEvent(LocalDateTime.now())
        this.eventPublisher.publishEvent(currentSeasonEvent)
        val currentSeason = currentSeasonEvent.response

        val userQuestHistory = userQuestHistoryRepository.findByUserIdAndQuestAndStatusNot(userId, quest, QuestHistoryStatus.COMPLETE)
            ?: UserQuestHistoryEntity(
                userId = userId,
                quest = quest,
                seasonId = currentSeason.seasonId,
            )


        return userQuestHistoryRepository.save(userQuestHistory)
    }

    @Transactional
    fun complete(questHistory: UserQuestHistoryEntity) {
        questHistory.complete()

        if (questHistory.status == QuestHistoryStatus.COMPLETE) {
            val event = MetroAreaGetByCommercialAreaEvent(commercialAreaCode = questHistory.quest.commercialAreaCode)
            this.eventPublisher.publishEvent(event)
            val metroAreaCode = event.response.metroAreaCode

            val userPointCreateRequests = questHistory.quest.rewards
                .filter { it.rewardType == RewardType.POINT }
                .map {
                    UserPointCreateRequest(
                        metroAreaCode = metroAreaCode,
                        commercialAreaCode = questHistory.quest.commercialAreaCode,
                        pointType = it.pointType,
                        point = it.point
                    )
                }
            this.eventPublisher.publishEvent(
                UserPointCreateEvent(
                    userId = questHistory.userId,
                    questId = questHistory.quest.id!!,
                    request = userPointCreateRequests,
                    seasonId = questHistory.seasonId,
                    userQuestHistoryId = questHistory.id,
                )
            )

            val maxStreakDay = userQuestHistoryRepository.findMaxConsecutiveDays(questHistory.userId)
            this.eventPublisher.publishEvent(
                UserTitleQuestCompleteEvent(
                    userId = questHistory.userId,
                    maxStreak = maxStreakDay
                )
            )
        }
    }

    fun findCustomerRank(userId: String, questId: Long): Int? =
        this.userQuestHistoryRepository.findCustomerRank(userId, questId)

    fun getCompletedQuestHistoryCount(seasonId: Long?, userId: String): Long {
        return seasonId?.let {
            this.userQuestHistoryRepository.countBySeasonIdAndUserId(it, userId)
        } ?: this.userQuestHistoryRepository.countByUserId(userId)
    }

    @Transactional
    fun deleteByUserId(userId: String) {
        this.userQuestHistoryRepository.deleteByUserId(userId)
    }

    private fun getDateRange(type: CouponType?): Pair<LocalDateTime, LocalDateTime> {
        val now = LocalDateTime.now(ZoneId.of("Asia/Seoul"))
        return when (type) {
            CouponType.WEEK -> {
                val lastWeekMonday = now.minusWeeks(1)
                    .with(java.time.temporal.TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY))
                    .withHour(0).withMinute(0).withSecond(0)
                val lastWeekSunday = lastWeekMonday.plusDays(6)
                    .withHour(23).withMinute(59).withSecond(59)
                Pair(lastWeekMonday, lastWeekSunday)
            }

            CouponType.MONTH -> {
                val lastMonth = now.minusMonths(1)
                val firstDayOfMonth = lastMonth.withDayOfMonth(1)
                    .withHour(0).withMinute(0).withSecond(0)
                val lastDayOfMonth = firstDayOfMonth
                    .with(java.time.temporal.TemporalAdjusters.lastDayOfMonth())
                    .withHour(23).withMinute(59).withSecond(59)
                Pair(firstDayOfMonth, lastDayOfMonth)
            }

            CouponType.SEASON -> {
                val yesterdaySeasonEvent = SeasonGetCurrentEvent(now.minusDays(1))
                this.eventPublisher.publishEvent(yesterdaySeasonEvent)
                val yesterdaySeason = yesterdaySeasonEvent.response
                val startDate = yesterdaySeason.startDate.withHour(0).withMinute(0).withSecond(0)
                val endDate = yesterdaySeason.endDate.withHour(23).withMinute(59).withSecond(59)
                Pair(startDate, endDate)
            }

            else -> Pair(now, now)
        }
    }

    fun getUserQuestHistoryRankingStore(type: CouponType?, storeId: Long?): List<String> {
        val (startDate, endDate) = getDateRange(type)
        return this.userQuestHistoryRepository.findAllUserRankByStore(startDate, endDate, storeId)
    }

    fun getUserQuestHistoryRankingMetro(type: CouponType?, storeId: Long?): List<String> {
        val (startDate, endDate) = getDateRange(type)

        val storeEvent = StoreInfoGetEvent(storeId)
        eventPublisher.publishEvent(storeEvent)
        val store = storeEvent.response

        val metroEvent = CommercialAreaByMetroAreaGetEvent(store.metroAreaCode)
        this.eventPublisher.publishEvent(metroEvent)
        val commercialArea = metroEvent.response
        val commercialAreaCodes = commercialArea.map { it.code }

        return this.userQuestHistoryRepository.findAllUserRankByArea(startDate, endDate, commercialAreaCodes)
    }

    fun getUserQuestHistoryRankingCommercial(type: CouponType?, storeId: Long?): List<String> {
        val (startDate, endDate) = getDateRange(type)

        val storeEvent = StoreInfoGetEvent(storeId)
        eventPublisher.publishEvent(storeEvent)
        val store = storeEvent.response

        val commercialAreaCodes = listOf(store.commercialAreaCode)

        return this.userQuestHistoryRepository.findAllUserRankByArea(startDate, endDate, commercialAreaCodes)
    }

    fun findLastCompleteHistoryByUserId(userId: String, questIds: List<Long>): List<UserQuestHistoryEntity>?{
        return userQuestHistoryRepository.findFirstByUserIdAndQuestIdOrderByCompletedAtDesc(userId, questIds)

    }
}
