package com.illsang.management.listener

import com.illsang.common.event.quest.TitleQuestCompleteEvent
import com.illsang.management.service.TitleService
import org.springframework.context.event.EventListener

class TitleEventListener(
    private val titleService: TitleService
) {

    @EventListener
    fun getTitleForQuestComplete (event : TitleQuestCompleteEvent){
        titleService.getTitleForQuestComplete(event.userId, event.maxStreak)
    }
}

