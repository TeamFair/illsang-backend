package com.illsang.management.dto.response

import com.illsang.management.domain.model.BannerModel

data class BannerResponse(
    val id: Long?,
    val title: String?,
    val bannerImageId: String?,
    val description: String?,
    val activeYn: Boolean?,
) {
    companion object {
        fun from(banner: BannerModel): BannerResponse {
            return BannerResponse(
                id = banner.id,
                title = banner.title,
                bannerImageId = banner.bannerImageId,
                description = banner.description,
                activeYn = banner.activeYn,
            )
        }
    }
}
