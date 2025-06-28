package com.teamfair.modulequest.domain.model

import com.illsang.common.model.BaseModel
import java.time.LocalDateTime

data class MissionModel(
    val id: Long? = null,
    var type: String,
    var title: String,
    var sortOrder: Int = 0,
    val questId: Long,
    override val createdBy: String? = null,
    override val createdAt: LocalDateTime? = null,
    override val updatedBy: String? = null,
    override val updatedAt: LocalDateTime? = null
) : BaseModel(createdBy, createdAt, updatedBy, updatedAt) 