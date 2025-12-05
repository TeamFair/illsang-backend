package com.illsang.quest.listener

import com.illsang.common.event.user.mission.ChangeUserMissionCommentStatusEvent
import com.illsang.quest.service.user.MissionHistoryCommentService
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class MissionHistoryCommentEventListener(
    private val missionHistoryCommentService: MissionHistoryCommentService
) {

    @EventListener
    fun changeReportStatus(event: ChangeUserMissionCommentStatusEvent){
        this.missionHistoryCommentService.changeReportStatus(event.id, event.status)
    }
}