package com.illsang.management.domain.model

import com.illsang.common.model.BaseModel
import java.time.LocalDateTime

data class ImageModel(
    val id: Long? = null,
    val type: String,
    val name: String,
    val size: Long,
    override val createdBy: String? = null,
    override val createdAt: LocalDateTime? = null,
    override val updatedBy: String? = null,
    override val updatedAt: LocalDateTime? = null
) : BaseModel(createdBy, createdAt, updatedBy, updatedAt) {
    fun validate() {
        require(type.isNotBlank()) { "Type is required" }
        require(name.isNotBlank()) { "Name is required" }
        require(size > 0) { "Size must be greater than 0" }
        require(type.length <= 50) { "Type must be less than 50 characters" }
        require(name.length <= 255) { "Name must be less than 255 characters" }
    }
} 