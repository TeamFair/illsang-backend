package com.illsang.quest.dto.request.quest

import com.illsang.common.enums.PointType
import com.illsang.quest.domain.entity.quest.QuestEntity
import com.illsang.quest.domain.entity.quest.QuestRewardEntity
import com.illsang.quest.enums.RewardType

data class QuestRewardCreateRequest(
    val questId: Long,
    val rewardType: RewardType,
    val pointType: PointType,
    val point: Int,
) {
    fun toEntity(quest: QuestEntity): QuestRewardEntity {
        return QuestRewardEntity(
            quest = quest,
            rewardType = this.rewardType,
            pointType = this.pointType,
            point = this.point
        )
    }
}

data class QuestRewardUpdateRequest(
    val rewardType: RewardType,
    val pointType: PointType,
    val point: Int,
)
