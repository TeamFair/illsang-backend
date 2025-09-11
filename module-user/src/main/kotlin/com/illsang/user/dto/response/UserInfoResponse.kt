package com.illsang.user.dto.response

import com.illsang.auth.enums.OAuthProvider
import com.illsang.common.enums.TitleGrade
import com.illsang.common.enums.TitleType
import com.illsang.user.domain.entity.UserEntity
import com.illsang.user.domain.model.UserModel
import com.illsang.user.domain.model.UserRankModel
import com.illsang.user.domain.model.UserTitleForPointModel
import com.illsang.user.domain.model.UserTitleModel
import com.illsang.user.enums.UserStatus
import java.time.LocalDateTime

data class UserInfoResponse(
    val id: String?,
    val email: String?,
    val channel: OAuthProvider?,
    val nickname: String?,
    val status: UserStatus?,
    val statusUpdatedAt: LocalDateTime?,
    val profileImageId: String?,
    val commercialAreaCode: String?,
    val title: UserTitleResponse?,
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
                title = user.title?.let { UserTitleResponse.from(it) },
            )
        }
    }
}

data class UserTitleResponse(
    val titleHistoryId: Long,
    val name: String,
    val grade: TitleGrade,
    val type: TitleType,
    val userId: String,

) {
    companion object {
        fun from(title: UserTitleModel): UserTitleResponse {
            return UserTitleResponse(
                titleHistoryId = title.id!!,
                name = title.titleName,
                grade = title.titleGrade,
                type = title.titleType,
                userId = title.userId,
            )
        }
    }
}

data class UserTitleAddInfoResponse(
    val titleHistoryId: Long,
    val name: String,
    val grade: TitleGrade,
    val type: TitleType,
    val userId: String,
    val createdAt: LocalDateTime? = null,
)

data class UserTitleForLegendResponse(
    val userId: String,
    val profileImageId: String?,
    val nickName: String?,
    val point: Long?,
    val rank: Long?,
    val title: UserTitleAddInfoResponse?,
){
    companion object{
        fun from(title: UserTitleForPointModel): UserTitleForLegendResponse {
            return UserTitleForLegendResponse(
                userId = title.userId,
                profileImageId = title.user.profileImageId,
                nickName = title.user.nickname,
                point = title.point,
                rank = title.rank,
                title = UserTitleAddInfoResponse(
                    titleHistoryId = title.id!!,
                    name = title.titleName,
                    grade = title.titleGrade,
                    type = title.titleType,
                    userId = title.userId,
                    createdAt = title.createdAt,
                )
            )
        }
    }
}


data class UserCommercialPointResponse(
    val topCommercialArea: UserTopCommercialPointResponse?,
    val totalOwnerContributions: List<UserCommercialContributionResponse>,
) {
    companion object {
        fun from(
            topCommercialArea: UserTopCommercialPointResponse?,
            totalOwnerContributionList: List<UserCommercialContributionResponse>
        ): UserCommercialPointResponse {
            return UserCommercialPointResponse(
                topCommercialArea = topCommercialArea,
                totalOwnerContributions = totalOwnerContributionList,
            )
        }
    }
}

data class UserCommercialContributionResponse(
    val commercialAreaCode: String,
    val point: Long,
) {
    companion object {
        fun from(code: String, point: Long): UserCommercialContributionResponse {
            return UserCommercialContributionResponse(
                commercialAreaCode = code,
                point = point,
            )
        }
    }
}

data class UserTopCommercialPointResponse(
    val commercialAreaCode: String,
    val point: Long,
    val ownerContributionPercent: Double,
) {
    companion object {
        fun from(code: String, point: Long, contributionPercent: Double): UserTopCommercialPointResponse {
            return UserTopCommercialPointResponse(
                commercialAreaCode = code,
                point = point,
                ownerContributionPercent = contributionPercent,
            )
        }
    }
}

data class UserPointStatisticResponse(
    val completedQuestCount: Long,
    val metroAreaPoint: Long,
    val commercialAreaPoint: Long,
    val contributionPoint: Long,
) {
    companion object {
        fun from(
            completedQuestCount: Long,
            metroAreaPoint: Long,
            commercialAreaPoint: Long,
            contributionPoint: Long,
        ): UserPointStatisticResponse {
            return UserPointStatisticResponse(
                completedQuestCount = completedQuestCount,
                metroAreaPoint = metroAreaPoint,
                commercialAreaPoint = commercialAreaPoint,
                contributionPoint = contributionPoint,
            )
        }
    }
}

data class UserPointSummaryResponse(
    val topMetroAreaCode: String?,
    val topCommercialAreaCode: String?,
    val topContributionPoint: Long?
) {
    companion object {
        fun from(
            topMetroAreaCode: String?,
            topCommercialAreaCode: String?,
            topContributionPoint: Long?
        ): UserPointSummaryResponse {
            return UserPointSummaryResponse(
                topMetroAreaCode = topMetroAreaCode,
                topCommercialAreaCode = topCommercialAreaCode,
                topContributionPoint = topContributionPoint,
            )
        }
    }
}
