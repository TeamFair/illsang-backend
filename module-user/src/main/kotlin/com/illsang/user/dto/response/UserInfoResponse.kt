package com.illsang.user.dto.response

import com.illsang.auth.enums.OAuthProvider
import com.illsang.common.enums.TitleGrade
import com.illsang.common.enums.TitleType
import com.illsang.user.domain.entity.UserTitleEntity
import com.illsang.user.domain.model.UserModel
import com.illsang.user.enums.UserStatus
import java.time.LocalDateTime

data class UserInfoResponse (
    val id: String?,
    val email: String?,
    val channel: OAuthProvider?,
    val nickname: String?,
    val status: UserStatus?,
    val statusUpdatedAt: LocalDateTime?,
    val profileImageId: String?,
    val commercialAreaCode: String?,
) {
    companion object {
        fun from(user: UserModel): UserInfoResponse {
            return UserInfoResponse(
                id = user.id,
                email = user.email,
                channel = user.channel,
                nickname = user.nickname,
                status = user.status,
                statusUpdatedAt = user.updatedAt,
                profileImageId = user.profileImageId,
                commercialAreaCode = user.commercialAreaCode,
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
        fun from(title: UserTitleEntity): UserTitleResponse {
            return UserTitleResponse(
                name = title.titleName,
                grade = title.titleGrade,
                type = title.titleType,
            )
        }
    }
}
