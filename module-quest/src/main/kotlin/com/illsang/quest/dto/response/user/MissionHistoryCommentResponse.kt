package com.illsang.quest.dto.response.user

import com.illsang.common.enums.ReportStatusType
import com.illsang.common.event.user.info.UserInfoGetEvent
import com.illsang.common.event.user.info.UserInfoGetEvent.UserTitleInfo
import com.illsang.quest.domain.entity.user.UserMissionHistoryCommentEntity
import com.illsang.quest.enums.MissionHistoryCommentStatus

data class MissionHistoryCommentResponse(
    val id: Long,
    val parentId: Long?,
    val comment: String,
    val writer: WriterResponse,
    val status: ReportStatusType,
    val createdAt: String,
    val hasReportedYn: Boolean = false,
    val children: MutableList<MissionHistoryCommentResponse> = mutableListOf(),

    ) {

    companion object {
        fun from(
            userInfo: UserInfoGetEvent.UserInfo,
            userMissionHistoryCommentEntity: UserMissionHistoryCommentEntity,
            hasReportedYn: Boolean,
        ): MissionHistoryCommentResponse {
            return MissionHistoryCommentResponse(
                id = userMissionHistoryCommentEntity.id!!,
                parentId = userMissionHistoryCommentEntity.parentId,
                comment = userMissionHistoryCommentEntity.comment,
                writer = WriterResponse.from(userInfo),
                createdAt = userMissionHistoryCommentEntity.createdAt!!.toString(),
                status = userMissionHistoryCommentEntity.status,
                hasReportedYn = hasReportedYn
            )
        }
    }
}

data class WriterResponse(
    val userId: String,
    val nickname: String,
    val profileImageId: String?,
    val title: UserTitleInfo?,
    ) {
    companion object {
        fun from(userInfo: UserInfoGetEvent.UserInfo): WriterResponse {
            return WriterResponse(
                userId =userInfo.userId,
                nickname = userInfo.nickname,
                profileImageId = userInfo.profileImageId,
                title = userInfo.title,
            )
        }
    }
}