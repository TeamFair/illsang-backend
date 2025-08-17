package com.illsang.management.dto.response

import com.illsang.management.domain.model.BannerModel

data class BannerResponse(
    val id: Long?,
    val title: String?,
    val navigationTitle: String?,
    val bannerImageId: String?,
    val description: String?,
    val useYn: Boolean?,
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
            )
        }
    }
}
