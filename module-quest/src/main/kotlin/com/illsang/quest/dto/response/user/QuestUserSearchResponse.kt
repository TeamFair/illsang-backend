package com.illsang.quest.dto.response.user

import com.illsang.common.enums.PointType
import com.illsang.quest.domain.entity.quest.QuestEntity
import com.illsang.quest.domain.entity.quest.QuestRewardEntity
import com.illsang.quest.domain.entity.user.UserQuestFavoriteEntity
import com.illsang.quest.enums.QuestRepeatFrequency
import com.illsang.quest.enums.QuestType
import java.time.LocalDateTime

data class QuestUserPopularResponse(
    val questId: Long?,
    val questType: QuestType?,
    val repeatFrequency: QuestRepeatFrequency?,
    val title: String?,
    val writerName: String?,
    var mainImageId: String?,
    var imageId: String?,
    val expireDate: LocalDateTime?,
) {
    companion object {
        fun from(quest: QuestEntity): QuestUserPopularResponse {
            return QuestUserPopularResponse(
                questId = quest.id,
                questType = quest.type,
                repeatFrequency = quest.repeatFrequency,
                title = quest.title,
                writerName = quest.writerName,
                mainImageId = quest.mainImageId,
                imageId = quest.imageId,
                expireDate = quest.expireDate,
            )
        }
    }
}

data class QuestUserRecommendResponse(
    val questId: Long?,
    val title: String?,
    val writerName: String?,
    var mainImageId: String?,
    var imageId: String?,
) {
    companion object {
        fun from(quest: QuestEntity): QuestUserRecommendResponse {
            return QuestUserRecommendResponse(
                questId = quest.id,
                title = quest.title,
                writerName = quest.writerName,
                mainImageId = quest.mainImageId,
                imageId = quest.imageId,
            )
        }
    }
}

data class QuestUserRewardResponse(
    val questId: Long?,
    val title: String?,
    val writerName: String?,
    var mainImageId: String?,
    var imageId: String?,
    val expireDate: LocalDateTime?,
    var rewards: List<QuestUserReward>,
) {
    companion object {
        fun from(quest: QuestEntity): QuestUserRewardResponse {
            return QuestUserRewardResponse(
                questId = quest.id,
                title = quest.title,
                writerName = quest.writerName,
                mainImageId = quest.mainImageId,
                imageId = quest.imageId,
                expireDate = quest.expireDate,
                rewards = quest.rewards.map { QuestUserReward.from(it) }
            )
        }
    }
}

data class QuestUserTypeResponse(
    val questId: Long?,
    val title: String?,
    val writerName: String?,
    var mainImageId: String?,
    var imageId: String?,
    val expireDate: LocalDateTime?,
    var rewards: List<QuestUserReward>,
    var favoriteYn: Boolean,
    val questType: QuestType?,
    val repeatFrequency: QuestRepeatFrequency?,
) {
    companion object {
        fun from(quest: QuestEntity, favorite: UserQuestFavoriteEntity?): QuestUserTypeResponse {
            return QuestUserTypeResponse(
                questId = quest.id,
                title = quest.title,
                writerName = quest.writerName,
                mainImageId = quest.mainImageId,
                imageId = quest.imageId,
                expireDate = quest.expireDate,
                rewards = quest.rewards.map { QuestUserReward.from(it) },
                favoriteYn = favorite?.let { true } ?: false,
                questType = quest.type,
                repeatFrequency = quest.repeatFrequency,
            )
        }
    }
}

data class QuestUserBannerResponse(
    val questId: Long?,
    val title: String?,
    val writerName: String?,
    var mainImageId: String?,
    var imageId: String?,
    val expireDate: LocalDateTime?,
    var rewards: List<QuestUserReward>,
    val questType: QuestType?,
    val repeatFrequency: QuestRepeatFrequency?,
) {
    companion object {
        fun from(quest: QuestEntity): QuestUserBannerResponse {
            return QuestUserBannerResponse(
                questId = quest.id,
                title = quest.title,
                writerName = quest.writerName,
                mainImageId = quest.mainImageId,
                imageId = quest.imageId,
                expireDate = quest.expireDate,
                rewards = quest.rewards.map { QuestUserReward.from(it) },
                questType = quest.type,
                repeatFrequency = quest.repeatFrequency,
            )
        }
    }
}

data class QuestUserReward(
    val pointType: PointType,
    val point: Int,
) {
    companion object {
        fun from(reward: QuestRewardEntity): QuestUserReward {
            return QuestUserReward(
                pointType = reward.pointType,
                point = reward.point,
            )
        }
    }
}
