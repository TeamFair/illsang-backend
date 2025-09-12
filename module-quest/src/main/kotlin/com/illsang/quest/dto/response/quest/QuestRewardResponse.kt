package com.illsang.quest.dto.response.quest

import com.illsang.common.enums.PointType
import com.illsang.quest.domain.model.quset.CouponModel
import com.illsang.quest.domain.model.quset.QuestRewardModel
import com.illsang.quest.enums.RewardType
import java.time.LocalDateTime

data class QuestRewardResponse(
    val id: Long?,
    val rewardType: RewardType,
    val pointType: PointType?,
    val point: Int,
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
                point = questRewardModel.point,
                questId = questRewardModel.questId,
                createdBy = questRewardModel.createdBy,
                createdAt = questRewardModel.createdAt,
                updatedBy = questRewardModel.updatedBy,
                updatedAt = questRewardModel.updatedAt,
            )
        }
    }
}


data class QuestCouponRewardResponse(
    val id: Long?,
    val createdBy: String?,
    val createdAt: LocalDateTime?,
    val updatedBy: String?,
    val updatedAt: LocalDateTime?,
    val coupons: List<CouponResponse>?,
){
    companion object{
        fun from(questRewardModel: QuestRewardModel, couponModels: List<CouponModel>?): QuestCouponRewardResponse{
            return QuestCouponRewardResponse(
                id = questRewardModel.id,
                createdBy = questRewardModel.createdBy,
                createdAt = questRewardModel.createdAt,
                updatedBy = questRewardModel.updatedBy,
                updatedAt = questRewardModel.updatedAt,
                coupons = couponModels?.map{ CouponResponse.from(it)}
            )
        }
    }
}