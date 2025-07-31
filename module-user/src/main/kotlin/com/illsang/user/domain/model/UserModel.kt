package com.illsang.user.domain.model

import com.illsang.auth.enums.OAuthProvider
import com.illsang.common.domain.model.BaseModel
import com.illsang.user.domain.entity.UserEntity
import com.illsang.user.enums.UserStatus
import java.time.LocalDateTime

data class UserModel(
    val id: String? = null,
    val email: String,
    val channel: OAuthProvider,
    val nickname: String,
    val status: UserStatus,
    val statusUpdatedAt: LocalDateTime? = null,
    val profileImageId: String? = null,
    val commercialAreaCode: String? = null,
    val roles: List<String> = emptyList(),
    override val createdBy: String? = null,
    override val createdAt: LocalDateTime? = null,
    override val updatedBy: String? = null,
    override val updatedAt: LocalDateTime? = null
) : BaseModel(createdBy, createdAt, updatedBy, updatedAt) {
    companion object {
        fun from(user: UserEntity): UserModel {
            return UserModel(
                id = user.id,
                email = user.email,
                channel = user.channel,
                nickname = user.nickname,
                status = user.status,
                statusUpdatedAt = user.updatedAt,
                profileImageId = user.profileImageId,
                commercialAreaCode = user.commercialAreaCode,
                roles = user.roles,
                createdBy = user.createdBy,
                createdAt = user.createdAt,
                updatedBy = user.updatedBy,
                updatedAt = user.updatedAt,
            )
        }
    }
}
