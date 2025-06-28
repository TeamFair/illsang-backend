package com.illsang.management.application.port.out

import com.illsang.management.domain.model.BannerModel

interface BannerPersistencePort {
    fun save(bannerModel: BannerModel): BannerModel
    fun findById(id: Long): BannerModel?
    fun findAll(): List<BannerModel>
    fun findByActiveYn(activeYn: Boolean): List<BannerModel>
    fun deleteById(id: Long)
    fun existsById(id: Long): Boolean
} 