package com.illsang.quest.domain.model

import com.illsang.common.domain.model.BaseModel
import com.illsang.quest.domain.entity.QuestRewardEntity
import com.illsang.quest.enums.RewardType
import java.time.LocalDateTime

data class QuestRewardModel(
    val id: Long? = null,
    var type: RewardType,
    var amount: Int,
    val questId: Long,
    override val createdBy: String? = null,
    override val createdAt: LocalDateTime? = null,
    override val updatedBy: String? = null,
    override val updatedAt: LocalDateTime? = null
) : BaseModel(createdBy, createdAt, updatedBy, updatedAt) {

    companion object {
        fun from(questReward: QuestRewardEntity): QuestRewardModel {
            return QuestRewardModel(
                id = questReward.id,
                type = questReward.type,
                amount = questReward.amount,
                questId = questReward.quest.id!!,
                createdBy = questReward.createdBy,
                createdAt = questReward.createdAt,
                updatedBy = questReward.updatedBy,
                updatedAt = questReward.updatedAt,
            )
        }
    }

}
