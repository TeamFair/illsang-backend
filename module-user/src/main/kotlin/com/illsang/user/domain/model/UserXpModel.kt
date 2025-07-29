package com.illsang.user.domain.model

import com.illsang.common.domain.model.BaseModel
import com.illsang.user.domain.entity.UserXpEntity
import com.illsang.user.enums.XpType
import java.time.LocalDateTime

data class UserXpModel(
    val id: Long? = null,
    val userId: Long,
    val xpType: XpType,
    val point: Int,
    override val createdBy: String? = null,
    override val createdAt: LocalDateTime? = null,
    override val updatedBy: String? = null,
    override val updatedAt: LocalDateTime? = null
) : BaseModel(createdBy, createdAt, updatedBy, updatedAt) {
    init {
        require(point >= 0) { "Point must be greater than or equal to 0" }
    }

    companion object {
        fun from(userXp: UserXpEntity): UserXpModel {
            return UserXpModel(
                id = userXp.id,
                userId = userXp.userEntity.id!!,
                xpType = userXp.xpType,
                point = userXp.point,
                createdBy = userXp.createdBy,
                createdAt = userXp.createdAt,
                updatedBy = userXp.updatedBy,
                updatedAt = userXp.updatedAt,
            )
        }
    }
}
