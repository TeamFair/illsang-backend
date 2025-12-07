package com.illsang.quest.dto.response.user

import com.illsang.common.enums.PointType
import com.illsang.common.event.user.info.UserInfoGetEvent
import com.illsang.common.event.user.point.UserPointHistoryGetEvent
import com.illsang.quest.domain.entity.user.UserMissionHistoryEmojiEntity
import com.illsang.quest.domain.entity.user.UserMissionHistoryEntity
import com.illsang.quest.domain.model.quset.QuizHistoryModel
import com.illsang.quest.dto.response.quest.QuizHistoryResponse
import com.illsang.quest.enums.EmojiType
import com.illsang.quest.enums.MissionType
import com.illsang.quest.enums.QuestRepeatFrequency
import com.illsang.quest.enums.QuestType
import java.time.LocalDateTime

data class MissionHistoryRandomResponse(
    val missionHistoryId: Long?,
    val user: UserResponse,
    val title: String,
    val submitImageId: String?,
    val commercialAreaCode: String,
    val questType: QuestType,
    val repeatFrequency: QuestRepeatFrequency?,
    val writerName: String?,
    val expireDate: LocalDateTime?,
    val lastCompleteDate: LocalDateTime?,
    val likeCount: Int,
    val hateCount: Int,
    val viewCount: Int,
    val shareCount: Int,
    val commentCount: Int,
    val currentUserEmojis: List<EmojiType>,
    val createdAt: LocalDateTime,
) {
    companion object {
        fun from(
            missionHistory: UserMissionHistoryEntity,
            userInfo: UserInfoGetEvent.UserInfo,
            userEmojiHistory: List<UserMissionHistoryEmojiEntity>,
            commentCount: Int,
            lastCompleteDate: LocalDateTime?,
        ): MissionHistoryRandomResponse {
            return MissionHistoryRandomResponse(
                missionHistoryId = missionHistory.id,
                user = UserResponse.from(userInfo),
                title = missionHistory.mission.quest.title,
                submitImageId = missionHistory.submitImageId,
                commercialAreaCode = missionHistory.mission.quest.commercialAreaCode,
                questType = missionHistory.mission.quest.type,
                repeatFrequency = missionHistory.mission.quest.repeatFrequency,
                writerName = missionHistory.mission.quest.writerName,
                expireDate = missionHistory.mission.quest.expireDate,
                lastCompleteDate = lastCompleteDate,
                currentUserEmojis = userEmojiHistory.filter { it.missionHistory.id == missionHistory.id }.map { it.type },
                likeCount = missionHistory.likeCount,
                hateCount = missionHistory.hateCount,
                viewCount = missionHistory.viewCount,
                shareCount = missionHistory.shareCount,
                commentCount = commentCount,
                createdAt = missionHistory.createdAt!!,
            )
        }
    }
}

data class MissionHistoryExampleResponse(
    val missionHistoryId: Long?,
    val user: UserResponse,
    val title: String,
    val submitImageId: String?,
    val commercialAreaCode: String,
    val likeCount: Int,
    val hateCount: Int,
    val viewCount: Int,
    val commentCount: Int,
    val shareCount: Int,
    val currentUserEmojis: List<EmojiType>,
    val createdAt: LocalDateTime,
) {
    companion object {
        fun from(
            missionHistory: UserMissionHistoryEntity,
            userInfo: UserInfoGetEvent.UserInfo,
            userEmojiHistory: List<UserMissionHistoryEmojiEntity>,
            commentCount: Int,
        ): MissionHistoryExampleResponse {
            return MissionHistoryExampleResponse(
                missionHistoryId = missionHistory.id,
                user = UserResponse.from(userInfo),
                title = missionHistory.mission.quest.title,
                submitImageId = missionHistory.submitImageId,
                commercialAreaCode = missionHistory.mission.quest.commercialAreaCode,
                currentUserEmojis = userEmojiHistory.filter { it.missionHistory.id == missionHistory.id }.map { it.type },
                likeCount = missionHistory.likeCount,
                hateCount = missionHistory.hateCount,
                viewCount = missionHistory.viewCount,
                commentCount = commentCount,
                shareCount = missionHistory.shareCount,
                createdAt = missionHistory.createdAt!!,
            )
        }
    }
}

data class MissionHistoryOwnerResponse(
    val missionHistoryId: Long?,
    val title: String,
    val submitImageId: String?,
    val questImageId: String?,
    val viewCount: Int = 0,
    val likeCount: Int = 0,
    val createdAt: LocalDateTime,
    val commercialAreaCode: String,
    val missionType: MissionType,
    val repeatFrequency: QuestRepeatFrequency? = null,
    val questType: QuestType? = null,
) {
    companion object {
        fun from(missionHistory: UserMissionHistoryEntity): MissionHistoryOwnerResponse {
            return MissionHistoryOwnerResponse(
                missionHistoryId = missionHistory.id,
                title = missionHistory.mission.quest.title,
                submitImageId = missionHistory.submitImageId,
                questImageId = missionHistory.mission.quest.imageId,
                viewCount = missionHistory.viewCount,
                likeCount = missionHistory.likeCount,
                createdAt = missionHistory.createdAt!!,
                commercialAreaCode = missionHistory.mission.quest.commercialAreaCode,
                missionType = missionHistory.mission.type,
                repeatFrequency = missionHistory.mission.quest.repeatFrequency,
                questType = missionHistory.mission.quest.type,
            )
        }
    }
}

data class MissionHistoryReportedResponse(
    val missionHistoryId: Long,
    val title: String,
    val submitImageId: String?,
    val nickname: String?,
    val createdAt: LocalDateTime,
) {
    companion object {
        fun from(entity: UserMissionHistoryEntity, users: List<UserInfoGetEvent.UserInfo>): MissionHistoryReportedResponse {
            return MissionHistoryReportedResponse(
                missionHistoryId = entity.id!!,
                title = entity.mission.quest.title,
                submitImageId = entity.submitImageId,
                nickname = users.find { it.userId == entity.userId }?.nickname,
                createdAt = entity.createdAt!!,
            )
        }
    }
}

data class MissionHistoryDetailResponse(
    val missionHistoryId: Long?,
    val title: String,
    val submitImageId: String?,
    val questImageId: String?,
    val viewCount: Int = 0,
    val likeCount: Int = 0,
    val createdAt: LocalDateTime,
    val commercialAreaCode: String,
    val missionType: MissionType,
    val writerName: String?,
    val commercialGainPoint: Int,
    val metroGainPoint: Int,
    val contributionGainPoint: Int,
    val quizList: List<QuizHistoryResponse?> = emptyList(),
    val questType: QuestType? = null,
    val repeatFrequency: QuestRepeatFrequency? = null,
    val contributionDoublePointYn: Boolean? = null,
) {
    companion object {
        fun from(
            missionHistory: UserMissionHistoryEntity,
            quizHistory: QuizHistoryModel?,
            userInfo: UserInfoGetEvent.UserInfo,
            userPointHistory: List<UserPointHistoryGetEvent.UserPointHistory>,
        ): MissionHistoryDetailResponse {
            return MissionHistoryDetailResponse(
                missionHistoryId = missionHistory.id,
                title = missionHistory.mission.quest.title,
                submitImageId = missionHistory.submitImageId,
                questImageId = missionHistory.mission.quest.imageId,
                viewCount = missionHistory.viewCount,
                likeCount = missionHistory.likeCount,
                createdAt = missionHistory.createdAt!!,
                commercialAreaCode = missionHistory.mission.quest.commercialAreaCode,
                missionType = missionHistory.mission.type,
                writerName = missionHistory.mission.quest.writerName,
                commercialGainPoint = userPointHistory.filter { it.pointType == PointType.COMMERCIAL }
                    .sumOf { it.point },
                metroGainPoint = userPointHistory.filter { it.pointType == PointType.METRO }
                    .sumOf { it.point },
                contributionGainPoint = userPointHistory.filter { it.pointType == PointType.CONTRIBUTION }
                    .sumOf { it.point },
                quizList = listOfNotNull(quizHistory?.let { QuizHistoryResponse.from(it) }),
                questType = missionHistory.mission.quest.type,
                repeatFrequency = missionHistory.mission.quest.repeatFrequency,
                contributionDoublePointYn = userPointHistory.any { it.pointType == PointType.CONTRIBUTION && it.userCommercialAreaCode == it.commercialAreaCode },
            )
        }
    }
}
