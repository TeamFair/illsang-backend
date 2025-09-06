package com.illsang.quest.listener

import com.illsang.common.event.management.quest.CompletedQuestHistoryCountGetEvent
import com.illsang.common.event.user.quest.UserQuestHistoryDeleteEvent
import com.illsang.quest.service.user.QuestHistoryService
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class QuestHistoryEventListener(
    private val questHistoryService: QuestHistoryService,
) {

    @EventListener
    fun getCompletedQuestHistoryCount(event: CompletedQuestHistoryCountGetEvent) {
        event.response = this.questHistoryService.getCompletedQuestHistoryCount(event.seasonId, event.userId)
    }

    @EventListener
    fun deleteQuestHistory(event: UserQuestHistoryDeleteEvent) {
        this.questHistoryService.deleteByUserId(event.userId)
    }

}
