package com.illsang.management.application.service

import com.illsang.management.application.command.CreateBannerCommand
import com.illsang.management.application.command.UpdateBannerCommand
import com.illsang.management.application.port.out.BannerPersistencePort
import com.illsang.management.domain.model.BannerModel
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class BannerService(
    private val bannerPersistencePort: BannerPersistencePort
) {

    /**
     * create banner
     */
    @Transactional
    fun createBanner(command: CreateBannerCommand): BannerModel {
        val bannerModel = BannerModel(
            title = command.title,
            bannerImageId = command.bannerImageId,
            description = command.description,
            activeYn = command.activeYn
        )
        bannerModel.validate()
        return bannerPersistencePort.save(bannerModel)
    }

    /**
     * find banner by ic
     */
    fun getBanner(id: Long): BannerModel {
        return bannerPersistencePort.findById(id) 
            ?: throw IllegalArgumentException("Banner not found with id: $id")
    }

    /**
     * find all banner
     */
    fun getAllBanners(): List<BannerModel> {
        return bannerPersistencePort.findAll()
    }

    /**
     * find active banner
     */
    fun getActiveBanners(): List<BannerModel> {
        return bannerPersistencePort.findByActiveYn(true)
    }

    /**
     * update banner
     */
    @Transactional
    fun updateBanner(command: UpdateBannerCommand): BannerModel {
        val existingBanner = bannerPersistencePort.findById(command.id)
            ?: throw IllegalArgumentException("Banner not found with id: ${command.id}")
        
        val updatedBanner = existingBanner.copy(
            title = command.title ?: existingBanner.title,
            bannerImageId = command.bannerImageId ?: existingBanner.bannerImageId,
            description = command.description ?: existingBanner.description,
            activeYn = command.activeYn ?: existingBanner.activeYn
        )
        updatedBanner.validate()
        return bannerPersistencePort.save(updatedBanner)
    }

    /**
     * delete banner
     */
    @Transactional
    fun deleteBanner(id: Long) {
        when {
            !bannerPersistencePort.existsById(id) -> {
                throw IllegalArgumentException("Banner not found with id: $id")
            }
            else -> bannerPersistencePort.deleteById(id)
        }
    }
} 