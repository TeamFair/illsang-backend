package com.illsang.user.listener

import com.illsang.common.event.user.point.UserPointCreateEvent
import com.illsang.user.service.UserPointService
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class UserPointEventListener(
    private val userPointService: UserPointService,
) {

    @EventListener
    fun createPoint(event: UserPointCreateEvent) {
        this.userPointService.createPoints(event.seasonId, event.userId, event.questId, event.request)
    }

}
