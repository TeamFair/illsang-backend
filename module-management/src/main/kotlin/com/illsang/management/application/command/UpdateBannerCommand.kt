package com.illsang.management.application.command

data class UpdateBannerCommand(
    val id: Long,
    val title: String? = null,
    val bannerImageId: Long? = null,
    val description: String? = null,
    val activeYn: Boolean? = null
) 