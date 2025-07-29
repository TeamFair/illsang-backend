package com.illsang.user.dto.request

import com.illsang.user.domain.entity.UserEntity
import com.illsang.auth.enums.OAuthProvider
import com.illsang.user.enums.UserStatus

data class CreateUserRequest(
    val email: String,
    val channel: OAuthProvider,
    val nickname: String,
    val status: UserStatus,
) {
    fun toEntity(): UserEntity {
        return UserEntity(
            email = this.email,
            channel = this.channel,
            nickname = this.nickname,
            status = this.status,
        )
    }
}

data class UpdateUserNickNameRequest(
    val nickName: String,
)

data class UpdateUserProfileImageRequest(
    val imageId: String?,
)

