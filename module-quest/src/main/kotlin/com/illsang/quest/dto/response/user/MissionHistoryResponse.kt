package com.illsang.quest.dto.response.user

import com.illsang.common.event.user.info.UserInfoGetEvent
import com.illsang.quest.domain.entity.user.UserMissionHistoryEntity
import java.time.LocalDateTime

data class MissionHistoryRandomResponse (
    val missionHistoryId: Long?,
    val user: UserResponse,
    val title: String,
    val createdAt: LocalDateTime,
    val likeCount: Int,
    val hateCount: Int,
    val viewCount: Int,
) {
    companion object {
        fun from(missionHistory: UserMissionHistoryEntity, userInfo: UserInfoGetEvent.UserInfo): MissionHistoryRandomResponse {
            return MissionHistoryRandomResponse(
                missionHistoryId = missionHistory.id,
                user = UserResponse.from(userInfo),
                title = missionHistory.mission.quest.title,
                createdAt = missionHistory.createdAt!!,
                likeCount = missionHistory.likeCount,
                hateCount = missionHistory.hateCount,
                viewCount = missionHistory.viewCount,
            )
        }
    }
}
