package com.illsang.management.service

import com.illsang.common.event.management.image.ImageDeleteEvent
import com.illsang.common.event.management.image.ImageExistOrThrowEvent
import com.illsang.management.domain.entity.BannerEntity
import com.illsang.management.domain.model.BannerModel
import com.illsang.management.dto.request.BannerCreateRequest
import com.illsang.management.dto.request.BannerSearchRequest
import com.illsang.management.dto.request.BannerUpdateRequest
import com.illsang.management.repository.BannerRepository
import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class BannerService(
    private val eventPublisher: ApplicationEventPublisher,
    private val bannerRepository: BannerRepository,
) {

    @Transactional
    fun createBanner(request: BannerCreateRequest): BannerModel {
        this.eventPublisher.publishEvent(ImageExistOrThrowEvent(request.imageId))

        val banner = request.toEntity()
        this.bannerRepository.save(banner)

        return BannerModel.from(banner)
    }

    @Transactional
    fun updateBanner(id: Long, request: BannerUpdateRequest): BannerModel {
        this.eventPublisher.publishEvent(ImageExistOrThrowEvent(request.imageId))
        val banner = this.findById(id)

        banner.update(request)

        return BannerModel.from(banner)
    }

    @Transactional
    fun deleteBanner(id: Long) {
        val banner = this.findById(id)

        this.bannerRepository.delete(banner)
        banner.bannerImageId?.let {
            this.eventPublisher.publishEvent(ImageDeleteEvent(it))
        }
    }

    fun search(request: BannerSearchRequest, pageable: Pageable): Page<BannerModel> {
        val banners = this.bannerRepository.findAllBySearch(request, pageable)

        return banners.map { BannerModel.from(it) }
    }

    fun existOrThrowBanner(bannerId: Long) {
        this.findById(bannerId)
    }

    private fun findById(id: Long): BannerEntity = (this.bannerRepository.findByIdOrNull(id)
        ?: throw IllegalArgumentException("Banner not found with id: $id"))

}
