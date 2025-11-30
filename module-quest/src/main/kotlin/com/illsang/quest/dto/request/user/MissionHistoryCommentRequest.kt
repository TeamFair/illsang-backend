package com.illsang.quest.dto.request.user

import com.illsang.quest.domain.entity.user.UserMissionHistoryCommentEntity
import com.illsang.quest.domain.entity.user.UserMissionHistoryEntity
import com.illsang.quest.enums.MissionHistoryCommentStatus

data class MissionHistoryCommentRequest(
    val comment: String,
    val parentId: Long? = null,
    val writerId: String,
    val status: MissionHistoryCommentStatus? = MissionHistoryCommentStatus.SUBMITTED,

){
    fun toEntity(missionHistory: UserMissionHistoryEntity): UserMissionHistoryCommentEntity {

        val missionHistoryComment = UserMissionHistoryCommentEntity(
            comment = this.comment,
            parentId = this.parentId,
            writerId = this.writerId,
            missionHistory = missionHistory,
        )

        return missionHistoryComment
    }
}

data class MissionHistoryCommentUpdateRequest(
    val comment: String,
)