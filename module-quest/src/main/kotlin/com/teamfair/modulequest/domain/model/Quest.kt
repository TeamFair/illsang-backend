package com.teamfair.modulequest.domain.model

import com.illsang.common.model.BaseModel
import java.time.LocalDateTime

data class Quest(
    val id: Long? = null,
    var imageId: Long? = null,
    var writerName: String? = null,
    var mainImageId: Long? = null,
    var popularYn: Boolean = false,
    var type: String,
    var repeatFrequency: String? = null,
    var sortOrder: Int = 0,
    override val createdBy: String? = null,
    override val createdAt: LocalDateTime? = null,
    override val updatedBy: String? = null,
    override val updatedAt: LocalDateTime? = null
) : BaseModel(createdBy, createdAt, updatedBy, updatedAt) 