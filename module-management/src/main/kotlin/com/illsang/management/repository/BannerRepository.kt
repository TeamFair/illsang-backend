package com.illsang.management.repository

import com.illsang.management.domain.entity.BannerEntity
import org.springframework.data.jpa.repository.JpaRepository

interface BannerRepository : JpaRepository<BannerEntity, Long>, BannerCustomRepository
