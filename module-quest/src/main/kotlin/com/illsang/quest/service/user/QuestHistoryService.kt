package com.illsang.quest.service.user

import com.illsang.common.event.management.area.MetroAreaGetByCommercialAreaEvent
import com.illsang.common.event.management.season.SeasonGetCurrentEvent
import com.illsang.common.event.user.title.UserTitleQuestCompleteEvent
import com.illsang.common.event.user.point.UserPointCreateEvent
import com.illsang.common.event.user.point.UserPointCreateRequest
import com.illsang.quest.domain.entity.quest.QuestEntity
import com.illsang.quest.domain.entity.user.UserQuestHistoryEntity
import com.illsang.quest.enums.QuestHistoryStatus
import com.illsang.quest.enums.RewardType
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
        val currentSeasonEvent = SeasonGetCurrentEvent(LocalDateTime.now(ZoneId.of("Asia/Seoul")))
        this.eventPublisher.publishEvent(currentSeasonEvent)
        val currentSeason = currentSeasonEvent.response

        val userQuestHistory = this.userQuestHistoryRepository.findByUserIdAndQuest(userId, quest)
        return userQuestHistoryRepository.findByUserIdAndQuest(userId, quest)
            ?: run {
                // 없으면 새로 생성 후 저장
                val newHistory = UserQuestHistoryEntity(
                    userId = userId,
                    quest = quest,
                    seasonId = currentSeason.seasonId,
                )
                userQuestHistoryRepository.save(newHistory)
            }
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

}
