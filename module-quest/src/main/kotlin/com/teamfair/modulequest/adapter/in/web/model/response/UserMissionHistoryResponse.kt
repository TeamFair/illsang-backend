package com.teamfair.modulequest.adapter.`in`.web.model.response

import com.teamfair.modulequest.domain.model.UserMissionHistory
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
        fun from(userMissionHistory: UserMissionHistory): UserMissionHistoryResponse {
            return UserMissionHistoryResponse(
                id = userMissionHistory.id,
                userId = userMissionHistory.userId,
                status = userMissionHistory.status,
                submissionImageUrl = userMissionHistory.submissionImageUrl,
                submittedAt = userMissionHistory.submittedAt,
                missionId = userMissionHistory.missionId,
                userQuestHistoryId = userMissionHistory.userQuestHistoryId,
                createdBy = userMissionHistory.createdBy,
                createdAt = userMissionHistory.createdAt,
                updatedBy = userMissionHistory.updatedBy,
                updatedAt = userMissionHistory.updatedAt
            )
        }
    }
} 