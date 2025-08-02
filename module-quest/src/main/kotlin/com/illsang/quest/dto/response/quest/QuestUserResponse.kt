package com.illsang.quest.dto.response.quest

import com.illsang.common.enums.PointType
import com.illsang.quest.domain.entity.quest.QuestEntity
import com.illsang.quest.domain.entity.quest.QuestRewardEntity
import com.illsang.quest.enums.QuestRepeatFrequency
import com.illsang.quest.enums.QuestType

data class QuestUserPopularResponse(
    val questId: Long?,
    val questType: QuestType?,
    val repeatFrequency: QuestRepeatFrequency?,
    val title: String?,
    val writerName: String?,
    var mainImageId: String?,
    var imageId: String?,
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
                rewards = quest.rewards.map { QuestUserReward.from(it) }
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
                pointType = reward.pointType!!,
                point = reward.point,
            )
        }
    }
}
