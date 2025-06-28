package com.illsang.management.adapter.out.persistence

import com.illsang.management.adapter.out.persistence.repository.BannerRepository
import com.illsang.management.application.port.out.BannerPersistencePort
import com.illsang.management.domain.mapper.BannerMapper
import com.illsang.management.domain.model.BannerModel
import org.springframework.stereotype.Component

@Component
class BannerPersistenceAdapter(
    private val bannerRepository: BannerRepository
) : BannerPersistencePort {
    
    override fun save(bannerModel: BannerModel): BannerModel {
        val entity = BannerMapper.toEntity(bannerModel)
        val savedEntity = bannerRepository.save(entity)
        return BannerMapper.toModel(savedEntity)
    }
    
    override fun findById(id: Long): BannerModel? {
        return bannerRepository.findById(id)
            .map { BannerMapper.toModel(it) }
            .orElse(null)
    }
    
    override fun findAll(): List<BannerModel> {
        return bannerRepository.findAll()
            .map { BannerMapper.toModel(it) }
    }
    
    override fun findByActiveYn(activeYn: Boolean): List<BannerModel> {
        return bannerRepository.findByActiveYn(activeYn)
            .map { BannerMapper.toModel(it) }
    }
    
    override fun deleteById(id: Long) {
        bannerRepository.deleteById(id)
    }
    
    override fun existsById(id: Long): Boolean {
        return bannerRepository.existsById(id)
    }
} 