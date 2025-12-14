package com.illsang.quest.listener

import com.illsang.common.enums.ReportStatusType
import com.illsang.common.event.user.mission.ChangeUserMissionCommentStatusEvent
import com.illsang.common.event.user.mission.UserMissionCommentGetReportStatusEvent
import com.illsang.quest.service.user.MissionHistoryCommentService
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class MissionHistoryCommentEventListener(
    private val missionHistoryCommentService: MissionHistoryCommentService
) {

    @EventListener
    fun changeReportStatus(event: ChangeUserMissionCommentStatusEvent){
        this.missionHistoryCommentService.changeReportStatus(event.id, event.status)
    }

    @EventListener
    fun getReportStatus(event: UserMissionCommentGetReportStatusEvent){
        val userComment = this.missionHistoryCommentService.findAllById(event.id)
        event.response = userComment.status
    }
}