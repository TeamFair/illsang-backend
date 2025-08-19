package com.illsang.quest.dto.response.user

import com.illsang.common.event.user.info.UserInfoGetEvent
import com.illsang.quest.domain.entity.user.UserMissionHistoryEmojiEntity
import com.illsang.quest.domain.entity.user.UserMissionHistoryEntity
import com.illsang.quest.enums.EmojiType
import java.time.LocalDateTime

data class MissionHistoryRandomResponse(
    val missionHistoryId: Long?,
    val user: UserResponse,
    val title: String,
    val submitImageId: String?,
    val commercialAreaCode: String,
    val likeCount: Int,
    val hateCount: Int,
    val viewCount: Int,
    val currentUserEmojis: List<EmojiType>,
    val createdAt: LocalDateTime,
) {
    companion object {
        fun from(
            missionHistory: UserMissionHistoryEntity,
            userInfo: UserInfoGetEvent.UserInfo,
            userEmojiHistory: List<UserMissionHistoryEmojiEntity>,
        ): MissionHistoryRandomResponse {
            return MissionHistoryRandomResponse(
                missionHistoryId = missionHistory.id,
                user = UserResponse.from(userInfo),
                title = missionHistory.mission.quest.title,
                submitImageId = missionHistory.submitImageId,
                commercialAreaCode = missionHistory.mission.quest.commercialAreaCode,
                currentUserEmojis = userEmojiHistory.filter { it.userId == userInfo.userId && it.missionHistory.id == missionHistory.id }.map { it.type },
                likeCount = missionHistory.likeCount,
                hateCount = missionHistory.hateCount,
                viewCount = missionHistory.viewCount,
                createdAt = missionHistory.createdAt!!,
            )
        }
    }
}

data class MissionHistoryOwnerResponse(
    val missionHistoryId: Long?,
    val title: String,
    val submitImageId: String?,
    val createdAt: LocalDateTime,
) {
    companion object {
        fun from(missionHistory: UserMissionHistoryEntity): MissionHistoryOwnerResponse {
            return MissionHistoryOwnerResponse(
                missionHistoryId = missionHistory.id,
                title = missionHistory.mission.quest.title,
                submitImageId = missionHistory.submitImageId,
                createdAt = missionHistory.createdAt!!,
            )
        }
    }
}
