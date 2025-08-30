package com.illsang.quest.listener

import com.illsang.common.event.management.quest.QuestImageExistOrThrowEvent
import com.illsang.quest.service.quest.QuestService
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class QuestEventListener(
    private val questService: QuestService
) {

    @EventListener
    fun existImageId(event : QuestImageExistOrThrowEvent){
        questService.existQuestImageId(event.imageId)
    }
}