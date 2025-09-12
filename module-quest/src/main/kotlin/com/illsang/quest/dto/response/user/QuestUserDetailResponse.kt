package com.illsang.quest.dto.response.user

import com.illsang.common.event.user.info.UserInfoGetEvent
import com.illsang.quest.domain.entity.quest.MissionEntity
import com.illsang.quest.domain.entity.quest.QuestEntity
import com.illsang.quest.domain.entity.user.UserMissionHistoryEntity
import com.illsang.quest.domain.model.quset.CouponModel
import com.illsang.quest.dto.response.quest.CouponResponse
import com.illsang.quest.dto.response.quest.CouponRewardResponse
import com.illsang.quest.enums.MissionType
import com.illsang.quest.enums.QuestRepeatFrequency
import com.illsang.quest.enums.QuestType
import java.time.LocalDateTime

data class QuestUserDetailResponse(
    val id: Long?,
    val questType: QuestType?,
    val repeatFrequency: QuestRepeatFrequency?,
    val writerName: String,
    val title: String?,
    var mainImageId: String?,
    var imageId: String?,
    var userRank: Int?,
    val expireDate: LocalDateTime?,
    val favoriteYn: Boolean?,
    val rewards: List<QuestUserReward>,
    val missions: List<MissionUserDetailResponse>,
    val coupons:List<CouponRewardResponse>,
) {
    companion object {
        fun from(
            quest: QuestEntity,
            userRank: Int?,
            favoriteYn: Boolean,
            missionExampleImages: List<UserMissionHistoryEntity>,
            user: UserInfoGetEvent.UserInfo,
            coupons: List<CouponModel>,
            storeName: String?
        ): QuestUserDetailResponse {
            return QuestUserDetailResponse(
                id = quest.id,
                questType = quest.type,
                repeatFrequency = quest.repeatFrequency,
                writerName = quest.writerName,
                title = quest.title,
                imageId = quest.imageId,
                mainImageId = quest.mainImageId,
                expireDate = quest.expireDate,
                favoriteYn = favoriteYn,
                userRank = userRank,
                rewards = quest.rewards.map { QuestUserReward.from(
                    it,
                    quest.commercialAreaCode == user.commercialAreaCode,
                ) },
                missions = quest.missions.map {
                    MissionUserDetailResponse.from(
                        mission = it,
                        exampleImages = missionExampleImages.filter { history -> it.id == history.mission.id },
                    )
                },
                coupons = coupons.map{
                    CouponRewardResponse.from(it,storeName)
                },
            )
        }
    }
}

data class MissionUserDetailResponse(
    val id: Long?,
    val title: String,
    val type: MissionType,
    val exampleImageIds: List<String>,
) {
    companion object {
        fun from(mission: MissionEntity, exampleImages: List<UserMissionHistoryEntity>): MissionUserDetailResponse {
            return MissionUserDetailResponse(
                id = mission.id,
                title = mission.title,
                type = mission.type,
                exampleImageIds = exampleImages.sortedByDescending { it.likeCount }.mapNotNull { it.submitImageId },
            )
        }
    }
}
