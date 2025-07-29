package com.illsang.quest.dto.request

import com.illsang.quest.domain.entity.QuestEntity
import com.illsang.quest.domain.entity.QuestRewardEntity
import com.illsang.quest.enums.RewardType

data class QuestRewardCreateRequest(
    val questId: Long,
    val type: RewardType,
    val amount: Int,
) {
    fun toEntity(quest: QuestEntity): QuestRewardEntity {
        return QuestRewardEntity(
            quest = quest,
            type = this.type,
            amount = this.amount
        )
    }
}

data class QuestRewardUpdateRequest(
    val type: RewardType,
    val amount: Int,
)
