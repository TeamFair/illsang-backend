package com.illsang.management.adapter.out.persistence.repository

import com.illsang.management.adapter.out.persistence.entity.BannerEntity
import org.springframework.data.jpa.repository.JpaRepository

interface BannerRepository : JpaRepository<BannerEntity, Long> {
    fun findByActiveYn(activeYn: Boolean): List<BannerEntity>
} 