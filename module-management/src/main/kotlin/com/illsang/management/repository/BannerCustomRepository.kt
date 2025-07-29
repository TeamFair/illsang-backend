package com.illsang.management.repository

import com.illsang.management.domain.entity.BannerEntity
import com.illsang.management.dto.request.BannerSearchRequest
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface BannerCustomRepository {
    fun findAllBySearch(request: BannerSearchRequest, pageable: Pageable): Page<BannerEntity>
}
