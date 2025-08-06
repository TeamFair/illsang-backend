package com.illsang.user.dto.response

import com.illsang.user.domain.entity.UserEntity

data class UserRankResponse(
    val userId: String?,
    val profileImageId: String?,
    val nickName: String?,
    val point: Long?,
    val title: UserTitleResponse?,
) {
    companion object {
        fun from(user: Pair<UserEntity, Long>): UserRankResponse {
            return UserRankResponse(
                userId = user.first.id!!,
                profileImageId = user.first.profileImageId,
                nickName = user.first.nickname,
                point = user.second,
                title = user.first.currentTitle?.let { UserTitleResponse.from(it) },
            )
        }
    }
}

data class MetroRankResponse(
    val metroCode: String,
    val areaName: String,
    val point: Long,
)

data class CommercialRankResponse(
    val commercialCode: String,
    val areaName: String,
    val point: Long,
)
