package com.illsang.management.domain.model

import com.illsang.common.domain.model.BaseModel
import com.illsang.management.domain.entity.BannerEntity
import java.time.LocalDateTime

data class BannerModel(
    val id: Long? = null,
    val title: String,
    val navigationTitle: String,
    val bannerImageId: String? = null,
    val description: String? = null,
    val useYn: Boolean = false,
    override val createdBy: String? = null,
    override val createdAt: LocalDateTime? = null,
    override val updatedBy: String? = null,
    override val updatedAt: LocalDateTime? = null
) : BaseModel(createdBy, createdAt, updatedBy, updatedAt) {

    companion object {
        fun from(banner: BannerEntity): BannerModel {
            return BannerModel(
                id = banner.id,
                title = banner.title,
                bannerImageId = banner.bannerImageId,
                navigationTitle = banner.navigationTitle,
                description = banner.description,
                useYn = banner.useYn,
                createdAt = banner.createdAt,
                createdBy = banner.createdBy,
                updatedAt = banner.updatedAt,
                updatedBy = banner.updatedBy,
            )
        }
    }

}
