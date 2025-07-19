package com.illsang.management.dto.request

import com.illsang.management.domain.entity.BannerEntity

data class BannerCreateRequest (
    val title: String,
    val imageId: String,
    val description: String,
    val activeYn: Boolean = true
) {
    fun toEntity(): BannerEntity {
        return BannerEntity(
            title = title,
            description = description,
            bannerImageId = imageId,
            activeYn = activeYn,
        )
    }
}

data class BannerUpdateRequest (
    val title: String,
    val description: String,
    val activeYn: Boolean,
)

data class BannerSearchRequest(
    val title: String? = null,
    val description: String? = null,
    val activeYn: Boolean? = null,
)
