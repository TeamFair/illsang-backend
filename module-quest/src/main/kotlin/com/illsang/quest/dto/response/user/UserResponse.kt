package com.illsang.quest.dto.response.user

import com.illsang.common.enums.TitleGrade
import com.illsang.common.enums.TitleType
import com.illsang.common.event.user.info.UserInfoGetEvent


data class UserResponse (
    val userId: String,
    val nickname: String,
    val profileImageId: String?,
    val title: UserTitleResponse?,
) {
    companion object {
        fun from(userInfo: UserInfoGetEvent.UserInfo): UserResponse {
            return UserResponse(
                userId = userInfo.userId,
                nickname = userInfo.nickname,
                profileImageId = userInfo.profileImageId,
                title = userInfo.title?.let { UserTitleResponse.from(it) },
            )
        }
    }
}

data class UserTitleResponse (
    val name: String,
    val grade: TitleGrade,
    val type: TitleType,
) {
    companion object {
        fun from(userTitleInfo: UserInfoGetEvent.UserTitleInfo): UserTitleResponse {
            return UserTitleResponse(
                name = userTitleInfo.name,
                grade = userTitleInfo.grade,
                type = userTitleInfo.type,
            )
        }
    }
}
