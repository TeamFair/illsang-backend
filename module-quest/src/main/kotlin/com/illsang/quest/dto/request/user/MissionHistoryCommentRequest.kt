package com.illsang.quest.dto.request.user

import com.illsang.common.enums.ReportStatusType
import com.illsang.quest.domain.entity.user.UserMissionHistoryCommentEntity
import com.illsang.quest.domain.entity.user.UserMissionHistoryEntity

data class MissionHistoryCommentRequest(
    val comment: String,
    val parentId: Long? = null,
){
    fun toEntity(missionHistory: UserMissionHistoryEntity, userId: String): UserMissionHistoryCommentEntity {

        val missionHistoryComment = UserMissionHistoryCommentEntity(
            comment = this.comment,
            parentId = this.parentId,
            writerId = userId,
            missionHistory = missionHistory,
            status = ReportStatusType.COMPLETED,
        )
        return missionHistoryComment
    }
}

data class ReportMissionHistoryCommentRequest(
    val reason: String? = null,
)

data class MissionHistoryCommentUpdateRequest(
    val comment: String,
)