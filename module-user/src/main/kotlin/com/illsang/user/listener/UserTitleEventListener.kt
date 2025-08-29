package com.illsang.user.listener

import com.illsang.common.event.user.title.UserTitleExistOrThrowEvent
import com.illsang.common.event.user.title.UserTitleQuestCompleteEvent
import com.illsang.common.event.user.title.UserTitleUserCreateEvent
import com.illsang.user.service.UserTitleService
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class UserTitleEventListener(
    private val userTitleService: UserTitleService
) {

    @EventListener
    fun evaluateUserTitleByQuest(event: UserTitleQuestCompleteEvent) {
        val titleId = userTitleService.getTitleIdForQuestComplete(event.maxStreak)
        userTitleService.createUserTitle(event.userId, titleId!!)
    }

    @EventListener
    fun createUserTitle(event: UserTitleUserCreateEvent) {
        userTitleService.createUserTitle(event.userId, event.titleId)
    }

    @EventListener
    fun existOrThrowUserTitle(event: UserTitleExistOrThrowEvent) {
        userTitleService.existOrThrowUserTitle(event.userTitleId)
    }
}