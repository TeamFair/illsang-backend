package com.illsang.management.listener

import com.illsang.common.event.management.banner.BannerExistOrThrowEvent
import com.illsang.management.service.BannerService
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class BannerEventListener(
    private val bannerService: BannerService,
) {

    @EventListener
    fun existOrThrowArea(event: BannerExistOrThrowEvent) {
        this.bannerService.existOrThrowBanner(event.bannerId)
    }

}
