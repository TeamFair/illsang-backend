package com.illsang.user.domain.model

import com.illsang.user.domain.entity.UserEntity

data class UserRankModel(
    val user: UserModel,
    val point: Long?,
    val rank: Long?,
) {
    companion object {
        fun from(user: UserEntity, point: Long? = null, rank: Long? = null): UserRankModel {
            return UserRankModel(
                user = UserModel.from(user),
                point = point,
                rank = rank,
            )
        }
    }
}
