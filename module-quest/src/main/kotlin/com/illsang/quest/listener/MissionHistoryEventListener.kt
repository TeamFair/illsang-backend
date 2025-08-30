package com.illsang.quest.listener

import com.illsang.common.event.user.mission.UserMissionImageExistOrThrowEvent
import com.illsang.quest.service.user.MissionHistoryService
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class MissionHistoryEventListener(
    private val missionHistoryService: MissionHistoryService
) {

    @EventListener
    fun existImageId(event : UserMissionImageExistOrThrowEvent){
        missionHistoryService.existUserMissionImageId(event.imageId)
    }
}