package com.illsang.management.controller

import com.illsang.management.dto.request.BannerCreateRequest
import com.illsang.management.dto.request.BannerSearchRequest
import com.illsang.management.dto.request.BannerUpdateRequest
import com.illsang.management.dto.response.BannerResponse
import com.illsang.management.service.BannerService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/banner")
class BannerController(
    private val bannerService: BannerService
) {

    @PostMapping
    fun createBanner(
        @RequestBody request: BannerCreateRequest,
    ): ResponseEntity<BannerResponse> {
        val banner = bannerService.createBanner(request)

        return ResponseEntity.status(HttpStatus.CREATED).body(
            BannerResponse.from(banner)
        )
    }

    @PutMapping("/{id}")
    fun updateBanner(
        @PathVariable id: Long,
        @RequestBody request: BannerUpdateRequest,
    ): ResponseEntity<BannerResponse> {
        val banner = this.bannerService.updateBanner(id, request)

        return ResponseEntity.ok(
            BannerResponse.from(banner)
        )
    }

    @DeleteMapping("/{id}")
    fun deleteBanner(
        @PathVariable id: Long,
    ): ResponseEntity<Void> {
        bannerService.deleteBanner(id)

        return ResponseEntity.ok().build()
    }

    @GetMapping
    fun findBySearch(
        request: BannerSearchRequest,
        @PageableDefault(size = 10) pageable: Pageable,
    ): ResponseEntity<Page<BannerResponse>> {
        val banners = this.bannerService.search(request, pageable)

        return ResponseEntity.ok(
            banners.map(BannerResponse::from)
        )
    }

}
