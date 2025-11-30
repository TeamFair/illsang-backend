package com.illsang.quest.dto.response.user

import com.illsang.common.event.user.info.UserInfoGetEvent
import com.illsang.quest.domain.entity.user.UserMissionHistoryCommentEntity
import com.illsang.quest.enums.MissionHistoryCommentStatus

data class MissionHistoryCommentResponse(
    val id: Long,
    val parentId: Long?,
    val comment: String,
    val writer: WriterResponse,
    val status: MissionHistoryCommentStatus,
    val reportedCount: Int,
    val createdAt: String,
    val children: MutableList<MissionHistoryCommentResponse> = mutableListOf(),

){

    companion object{
        fun from(
            userInfo: UserInfoGetEvent.UserInfo,
            userMissionHistoryCommentEntity: UserMissionHistoryCommentEntity,
        ): MissionHistoryCommentResponse{
            return MissionHistoryCommentResponse(
                id = userMissionHistoryCommentEntity.id!!,
                parentId = userMissionHistoryCommentEntity.parentId,
                comment = userMissionHistoryCommentEntity.comment,
                writer = WriterResponse(nickname = userInfo.nickname, id = userInfo.userId),
                createdAt = userMissionHistoryCommentEntity.createdAt!!.toString(),
                status = userMissionHistoryCommentEntity.status,
                reportedCount = userMissionHistoryCommentEntity.reportedCount,
            )
        }
    }
}

data class WriterResponse(
    val nickname: String,
    val id: String,
)