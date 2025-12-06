package com.illsang.quest.domain.model.quset

import com.illsang.common.domain.model.BaseModel
import com.illsang.common.enums.PointType
import com.illsang.quest.domain.entity.quest.QuestRewardEntity
import com.illsang.quest.enums.RewardType
import java.time.LocalDateTime

data class QuestRewardModel(
    val id: Long? = null,
    var rewardType: RewardType,
    var pointType: PointType?,
    var point: Int,
    val questId: Long,
    val couponId: Long? = null,
    override val createdBy: String? = null,
    override val createdAt: LocalDateTime? = null,
    override val updatedBy: String? = null,
    override val updatedAt: LocalDateTime? = null
) : BaseModel(createdBy, createdAt, updatedBy, updatedAt) {

    companion object {
        fun from(questReward: QuestRewardEntity): QuestRewardModel {
            return QuestRewardModel(
                id = questReward.id,
                rewardType = questReward.rewardType,
                pointType = questReward.pointType,
                point = questReward.point,
                questId = questReward.quest.id!!,
                couponId = questReward.couponId,
                createdBy = questReward.createdBy,
                createdAt = questReward.createdAt,
                updatedBy = questReward.updatedBy,
                updatedAt = questReward.updatedAt,
            )
        }
    }

}
