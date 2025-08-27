package com.illsang.user.listener

import com.illsang.common.event.quest.UserTitleQuestCompleteEvent
import com.illsang.user.service.UserTitleService
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class UserTitleEventListener(
    private val userTitleService : UserTitleService
) {

    @EventListener
    fun evaluateUserTitleByQuest(event: UserTitleQuestCompleteEvent){
        userTitleService.evaluateUserTitleByQuest(event.userId,event.titleId,event.titleName
            ,event.titleGrade,event.titleType)
    }
}