package com.illsang.user.dto.request

import com.illsang.auth.enums.OAuthProvider
import com.illsang.user.domain.entity.UserEntity
import com.illsang.user.enums.UserStatus

data class CreateUserRequest(
    val email: String,
    val channel: OAuthProvider,
    val nickname: String,
    val status: UserStatus,
    val roles: List<String> = listOf("USER"), // Default role for new users
) {
    fun toEntity(): UserEntity {
        return UserEntity(
            email = this.email,
            channel = this.channel,
            nickname = this.nickname,
            status = this.status,
            roles = this.roles,
        )
    }
}

data class UpdateUserNickNameRequest(
    val nickName: String,
)

data class UpdateUserProfileImageRequest(
    val imageId: String?,
)

data class UpdateUserTitleRequest(
    val titleHistoryId: String? = null,
)

data class UpdateUserAreaZoneRequest(
    val commercialAreaCode: String,
)
