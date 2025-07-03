package com.illsang.moduleuser.adapter.`in`.web.model.response

import com.illsang.moduleuser.domain.model.UserXpHistoryModel
import com.illsang.moduleuser.domain.model.XpType
import java.time.LocalDateTime

data class UserXpHistoryResponse(
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
        fun from(userXpHistoryModel: UserXpHistoryModel): UserXpHistoryResponse {
            return UserXpHistoryResponse(
                id = userXpHistoryModel.id,
                userId = userXpHistoryModel.userId,
                xpType = userXpHistoryModel.xpType,
                point = userXpHistoryModel.point,
                createdBy = userXpHistoryModel.createdBy,
                createdAt = userXpHistoryModel.createdAt,
                updatedBy = userXpHistoryModel.updatedBy,
                updatedAt = userXpHistoryModel.updatedAt
            )
        }
    }
}
