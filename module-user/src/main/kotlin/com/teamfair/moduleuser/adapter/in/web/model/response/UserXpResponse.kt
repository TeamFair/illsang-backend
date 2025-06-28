package com.teamfair.moduleuser.adapter.`in`.web.model.response

import com.teamfair.moduleuser.domain.model.UserXpModel
import com.teamfair.moduleuser.domain.model.XpType
import java.time.LocalDateTime

data class UserXpResponse(
    val id: Long?,
    val userId: Long,
    val xpType: XpType,
    val point: Int,
    val createdBy: String?,
    val createdAt: LocalDateTime?,
    val updatedBy: String?,
    val updatedAt: LocalDateTime?
) {
    companion object {
        fun from(userXpModel: UserXpModel): UserXpResponse {
            return UserXpResponse(
                id = userXpModel.id,
                userId = userXpModel.userId,
                xpType = userXpModel.xpType,
                point = userXpModel.point,
                createdBy = userXpModel.createdBy,
                createdAt = userXpModel.createdAt,
                updatedBy = userXpModel.updatedBy,
                updatedAt = userXpModel.updatedAt
            )
        }
    }
} 