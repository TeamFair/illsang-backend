package com.teamfair.moduleuser.domain.model

import com.illsang.common.model.BaseModel
import java.time.LocalDateTime
import java.util.*

data class UserEmojiModel(
    val id: Long? = null,
    val userId: Long,
    val targetId: UUID,
    val targetType: TargetType,
    override val createdBy: String? = null,
    override val createdAt: LocalDateTime? = null,
    override val updatedBy: String? = null,
    override val updatedAt: LocalDateTime? = null
) : BaseModel(createdBy, createdAt, updatedBy, updatedAt) 