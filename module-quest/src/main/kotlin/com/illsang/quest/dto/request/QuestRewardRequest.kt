package com.illsang.quest.dto.request

import com.illsang.quest.domain.entity.QuestEntity
import com.illsang.quest.domain.entity.QuestRewardEntity
import com.illsang.quest.enums.PointType
import com.illsang.quest.enums.RewardType

data class QuestRewardCreateRequest(
    val questId: Long,
    val rewardType: RewardType,
    val pointType: PointType?,
    val amount: Int,
) {
    fun toEntity(quest: QuestEntity): QuestRewardEntity {
        return QuestRewardEntity(
            quest = quest,
            rewardType = this.rewardType,
            pointType = this.pointType,
            amount = this.amount
        )
    }
}

data class QuestRewardUpdateRequest(
    val rewardType: RewardType,
    val pointType: PointType?,
    val amount: Int,
)
