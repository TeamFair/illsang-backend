package com.teamfair.modulequest.adapter.`in`.web.model.response

import com.teamfair.modulequest.domain.model.UserMissionHistoryModel
import com.teamfair.modulequest.domain.model.enums.MissionStatus
import java.time.LocalDateTime

data class UserMissionHistoryResponse(
    val id: Long?,
    val userId: Long,
    val status: MissionStatus,
    val submissionImageUrl: String?,
    val submittedAt: LocalDateTime?,
    val missionId: Long,
    val userQuestHistoryId: Long,
    val createdBy: String?,
    val createdAt: LocalDateTime?,
    val updatedBy: String?,
    val updatedAt: LocalDateTime?
) {
    companion object {
        fun from(userMissionHistoryModel: UserMissionHistoryModel): UserMissionHistoryResponse {
            return UserMissionHistoryResponse(
                id = userMissionHistoryModel.id,
                userId = userMissionHistoryModel.userId,
                status = userMissionHistoryModel.status,
                submissionImageUrl = userMissionHistoryModel.submissionImageUrl,
                submittedAt = userMissionHistoryModel.submittedAt,
                missionId = userMissionHistoryModel.missionId,
                userQuestHistoryId = userMissionHistoryModel.userQuestHistoryId,
                createdBy = userMissionHistoryModel.createdBy,
                createdAt = userMissionHistoryModel.createdAt,
                updatedBy = userMissionHistoryModel.updatedBy,
                updatedAt = userMissionHistoryModel.updatedAt
            )
        }
    }
} 