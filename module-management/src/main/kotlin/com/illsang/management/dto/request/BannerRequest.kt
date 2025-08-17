package com.illsang.management.dto.request

import com.illsang.management.domain.entity.BannerEntity

data class BannerCreateRequest (
    val title: String,
    val imageId: String,
    val description: String,
    val navigationTitle: String,
    val useYn: Boolean = false,
) {
    fun toEntity(): BannerEntity {
        return BannerEntity(
            title = title,
            description = description,
            navigationTitle = navigationTitle,
            bannerImageId = imageId,
            useYn = useYn,
        )
    }
}

data class BannerUpdateRequest (
    val title: String,
    val imageId: String,
    val navigationTitle: String,
    val description: String,
    val useYn: Boolean,
)

data class BannerSearchRequest(
    val title: String? = null,
    val description: String? = null,
    val useYn: Boolean? = null,
)
