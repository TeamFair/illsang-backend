package com.illsang.user.dto.response

import com.illsang.user.domain.model.UserRankModel

data class UserRankResponse(
    val userId: String?,
    val profileImageId: String?,
    val nickName: String?,
    val point: Long?,
    val rank: Long?,
    val title: UserTitleResponse?,
) {
    companion object {
        fun from(model: UserRankModel): UserRankResponse {
            return UserRankResponse(
                userId = model.user.id,
                profileImageId = model.user.profileImageId,
                nickName = model.user.nickname,
                point = model.point,
                rank = model.rank,
                title = model.user.title?.let { UserTitleResponse.from(it) },
            )
        }
    }
}

data class MetroRankResponse(
    val metroCode: String,
    val areaName: String,
    val point: Long,
    val images: List<String>,
    val rank: Int,
)

data class CommercialRankResponse(
    val commercialCode: String,
    val areaName: String,
    val point: Long,
    val images: List<String>,
    val rank: Int,
)

data class UserRankListResponse(
    val ranks: List<UserRankResponse>,
    val user: UserRankDetailResponse,
)

data class UserRankDetailResponse(
    val userId: String?,
    val profileImageId: String?,
    val nickName: String?,
    val point: Long?,
    val rank: Long?,
    val title: UserTitleResponse?,
    val pointGap: Long?,
) {
    companion object {
        fun from(model: UserRankModel, pointGap: Long?): UserRankDetailResponse {
            return UserRankDetailResponse(
                userId = model.user.id,
                profileImageId = model.user.profileImageId,
                nickName = model.user.nickname,
                point = model.point,
                rank = model.rank,
                title = model.user.title?.let { UserTitleResponse.from(it) },
                pointGap = pointGap,
            )
        }
    }
}
