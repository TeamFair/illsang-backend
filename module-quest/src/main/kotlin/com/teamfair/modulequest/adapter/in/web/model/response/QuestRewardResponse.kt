package com.teamfair.modulequest.adapter.`in`.web.model.response

import com.teamfair.modulequest.domain.model.QuestRewardModel
import com.teamfair.modulequest.domain.model.enums.RewardType
import java.time.LocalDateTime

data class QuestRewardResponse(
    val id: Long?,
    val type: RewardType,
    val amount: Int,
    val questId: Long,
    val createdBy: String?,
    val createdAt: LocalDateTime?,
    val updatedBy: String?,
    val updatedAt: LocalDateTime?
) {
    companion object {
        fun from(questRewardModel: QuestRewardModel): QuestRewardResponse {
            return QuestRewardResponse(
                id = questRewardModel.id,
                type = questRewardModel.type,
                amount = questRewardModel.amount,
                questId = questRewardModel.questId,
                createdBy = questRewardModel.createdBy,
                createdAt = questRewardModel.createdAt,
                updatedBy = questRewardModel.updatedBy,
                updatedAt = questRewardModel.updatedAt
            )
        }
    }
} 