package com.illsang.management.listener

import com.illsang.common.event.quest.TitleQuestCompleteEvent
import com.illsang.common.event.quest.TitleUserCreateEvent
import com.illsang.management.service.TitleService
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class TitleEventListener(
    private val titleService: TitleService
) {
    @Async
    @EventListener
    fun getTitleForQuestComplete (event : TitleQuestCompleteEvent){
        titleService.getTitleForQuestComplete(event.userId, event.maxStreak)
    }

    @Async
    @EventListener
    fun getTitleForUserCreate(event : TitleUserCreateEvent){
        titleService.getTitleForUserCreate(event.userId)

    }
}

