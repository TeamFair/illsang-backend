package com.illsang.quest.dto.response

import com.illsang.quest.domain.model.QuestRewardModel
import com.illsang.quest.enums.RewardType
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
                updatedAt = questRewardModel.updatedAt,
            )
        }
    }
}
