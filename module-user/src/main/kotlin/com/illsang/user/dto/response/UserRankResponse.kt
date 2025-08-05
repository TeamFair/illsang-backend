package com.illsang.user.dto.response

import com.illsang.user.domain.entity.UserEntity

data class UserRankTotalResponse (
    val userId: String?,
    val profileImageId: String?,
    val nickName: String?,
    val totalPoints: Long?,
) {
    companion object {
        fun from(user: Pair<UserEntity, Long>): UserRankTotalResponse {
            return UserRankTotalResponse(
                userId = user.first.id!!,
                profileImageId = user.first.profileImageId,
                nickName = user.first.nickname,
                totalPoints = user.second,
            )
        }
    }
}
