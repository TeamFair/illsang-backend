package com.teamfair.moduleuser.domain.model

import com.illsang.common.model.BaseModel
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
    fun validate() {
        require(point != 0) { "Point must not be 0" }
    }
} 