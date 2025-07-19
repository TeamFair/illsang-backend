package com.illsang.user.domain.model

import com.illsang.common.domain.model.BaseModel
import com.illsang.user.domain.entity.UserXpHistoryEntity
import com.illsang.user.enums.XpType
import java.time.LocalDateTime

data class UserXpHistoryModel(
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
        require(point != 0) { "Point must not be 0" }
    }
}
