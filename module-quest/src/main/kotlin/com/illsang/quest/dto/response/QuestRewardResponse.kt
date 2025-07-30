package com.illsang.quest.dto.response

import com.illsang.quest.domain.model.QuestRewardModel
import com.illsang.quest.enums.PointType
import com.illsang.quest.enums.RewardType
import java.time.LocalDateTime

data class QuestRewardResponse(
    val id: Long?,
    val rewardType: RewardType,
    val pointType: PointType?,
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
                rewardType = questRewardModel.rewardType,
                pointType = questRewardModel.pointType,
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
