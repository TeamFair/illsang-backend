package com.illsang.management.application.command

data class CreateBannerCommand(
    val title: String,
    val bannerImageId: Long? = null,
    val description: String? = null,
    val activeYn: Boolean = true
) 