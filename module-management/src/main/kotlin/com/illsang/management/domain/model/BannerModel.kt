package com.illsang.management.domain.model

import com.illsang.common.domain.model.BaseModel
import java.time.LocalDateTime

data class BannerModel(
    val id: Long? = null,
    val title: String,
    val bannerImageId: Long? = null,
    val description: String? = null,
    val activeYn: Boolean = true,
    override val createdBy: String? = null,
    override val createdAt: LocalDateTime? = null,
    override val updatedBy: String? = null,
    override val updatedAt: LocalDateTime? = null
) : BaseModel(createdBy, createdAt, updatedBy, updatedAt) {
    fun validate() {
        require(title.isNotBlank()) { "Title is required" }
        require(title.length <= 255) { "Title must be less than 255 characters" }
    }
}
