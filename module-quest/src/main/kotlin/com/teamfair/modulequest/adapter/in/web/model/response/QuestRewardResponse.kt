package com.teamfair.modulequest.adapter.`in`.web.model.response

import com.teamfair.modulequest.domain.model.QuestReward
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
        fun from(questReward: QuestReward): QuestRewardResponse {
            return QuestRewardResponse(
                id = questReward.id,
                type = questReward.type,
                amount = questReward.amount,
                questId = questReward.questId,
                createdBy = questReward.createdBy,
                createdAt = questReward.createdAt,
                updatedBy = questReward.updatedBy,
                updatedAt = questReward.updatedAt
            )
        }
    }
} 