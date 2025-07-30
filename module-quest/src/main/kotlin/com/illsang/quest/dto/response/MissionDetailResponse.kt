package com.illsang.quest.dto.response

import com.illsang.quest.domain.model.MissionDetailModel
import com.illsang.quest.domain.model.MissionModel
import com.illsang.quest.enums.MissionType
import java.time.LocalDateTime

data class MissionDetailResponse(
    val id: Long?,
    val type: MissionType,
    val sortOrder: Int?,
    val questId: Long,
    val quizzes: List<QuizDetailResponse>,
    val createdBy: String?,
    val createdAt: LocalDateTime?,
    val updatedBy: String?,
    val updatedAt: LocalDateTime?,
) {
    companion object {
        fun from(missionModel: MissionDetailModel): MissionDetailResponse {
            return MissionDetailResponse(
                id = missionModel.id,
                type = missionModel.type,
                sortOrder = missionModel.sortOrder,
                questId = missionModel.questId,
                quizzes = missionModel.quizzes.map { QuizDetailResponse.from(it) },
                createdBy = missionModel.createdBy,
                createdAt = missionModel.createdAt,
                updatedBy = missionModel.updatedBy,
                updatedAt = missionModel.updatedAt,
            )
        }
    }
}
