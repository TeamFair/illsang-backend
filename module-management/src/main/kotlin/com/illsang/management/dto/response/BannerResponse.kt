package com.illsang.management.dto.response

import com.illsang.management.domain.model.BannerModel
import java.time.LocalDateTime

data class BannerResponse(
    val id: Long?,
    val title: String?,
    val navigationTitle: String?,
    val bannerImageId: String?,
    val description: String?,
    val useYn: Boolean?,
    val createdBy: String?,
    val createdAt: LocalDateTime?,
    val updatedBy: String?,
    val updatedAt: LocalDateTime?,
) {
    companion object {
        fun from(banner: BannerModel): BannerResponse {
            return BannerResponse(
                id = banner.id,
                title = banner.title,
                bannerImageId = banner.bannerImageId,
                description = banner.description,
                navigationTitle = banner.navigationTitle,
                useYn = banner.useYn,
                createdAt = banner.createdAt,
                createdBy = banner.createdBy,
                updatedAt = banner.updatedAt,
                updatedBy = banner.updatedBy,
            )
        }
    }
}

data class BannerUserResponse(
    val id: Long?,
    val title: String?,
    val navigationTitle: String?,
    val bannerImageId: String?,
    val description: String?,
) {
    companion object {
        fun from(banner: BannerModel): BannerUserResponse {
            return BannerUserResponse(
                id = banner.id,
                title = banner.title,
                bannerImageId = banner.bannerImageId,
                description = banner.description,
                navigationTitle = banner.navigationTitle,
            )
        }
    }
}
