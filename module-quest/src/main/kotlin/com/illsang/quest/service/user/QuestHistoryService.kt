package com.illsang.quest.service.user

import com.illsang.common.event.management.area.MetroAreaGetByCommercialAreaEvent
import com.illsang.common.event.management.point.UserPointCreateEvent
import com.illsang.common.event.management.point.UserPointCreateRequest
import com.illsang.quest.domain.entity.quest.QuestEntity
import com.illsang.quest.domain.entity.user.UserQuestHistoryEntity
import com.illsang.quest.enums.QuestHistoryStatus
import com.illsang.quest.enums.RewardType
import com.illsang.quest.repository.user.QuestHistoryRepository
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class QuestHistoryService(
    private val userQuestHistoryRepository: QuestHistoryRepository,
    private val eventPublisher: ApplicationEventPublisher,
) {

    @Transactional
    fun findOrCreate(userId: String, quest: QuestEntity): UserQuestHistoryEntity {
        return this.userQuestHistoryRepository.findByUserIdAndQuest(userId, quest)
            ?: UserQuestHistoryEntity(
                userId = userId,
                quest = quest,
            )
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
                    request = userPointCreateRequests
                )
            )
        }
    }

    fun findCustomerRank(userId: String, questId: Long): Int? =
        this.userQuestHistoryRepository.findCustomerRank(userId, questId)

}
