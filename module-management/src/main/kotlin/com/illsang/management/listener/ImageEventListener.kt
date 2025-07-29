package com.illsang.management.listener

import com.illsang.common.event.management.image.ImageDeleteEvent
import com.illsang.common.event.management.image.ImageExistOrThrowEvent
import com.illsang.management.service.ImageService
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class ImageEventListener(
    private val imageService: ImageService,
) {

    @EventListener
    @Transactional
    fun deleteImage(event: ImageDeleteEvent) {
        this.imageService.deleteImage(event.imageId)
    }

    @EventListener
    fun existOrThrowImage(event: ImageExistOrThrowEvent) {
        this.imageService.existOrThrowImage(event.imageId)
    }

}
