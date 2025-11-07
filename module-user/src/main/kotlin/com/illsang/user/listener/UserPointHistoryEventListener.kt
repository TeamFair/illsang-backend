package com.illsang.user.listener

import com.illsang.common.event.user.point.UserPointHistoryGetEvent
import com.illsang.user.service.UserPointService
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class UserPointHistoryEventListener(
    private val userPointService: UserPointService,
) {

    @EventListener
    fun findAll(event: UserPointHistoryGetEvent){

        val userPointHistory = this.userPointService.findUserPointHistory(event.userQuestHistoryId)

        event.response = userPointHistory.map {
            UserPointHistoryGetEvent.UserPointHistory(
                userCommercialAreaCode = it.userCommercialAreaCode,
                commercialAreaCode = it.commercialAreaCode,
                point = it.point,
                pointType = it.pointType,
            )
        }

    }
}