package com.illsang.user.domain.model

import com.illsang.common.enums.PointType
import com.illsang.user.domain.entity.UserPointHistoryEntity

data class UserPointHistoryModel(
    val userCommercialAreaCode: String?,
    val commercialAreaCode: String,
    val point: Int,
    val pointType: PointType,
) {
    companion object{
        fun from(userPointHistory: UserPointHistoryEntity): UserPointHistoryModel{
            return UserPointHistoryModel(
                userCommercialAreaCode = userPointHistory.userCommercialAreaCode,
                commercialAreaCode = userPointHistory.commercialAreaCode,
                point = userPointHistory.point,
                pointType = userPointHistory.pointType,
            )
        }
    }
}