package com.illsang.management.controller

import com.illsang.management.dto.request.BannerCreateRequest
import com.illsang.management.dto.request.BannerSearchRequest
import com.illsang.management.dto.request.BannerUpdateRequest
import com.illsang.management.dto.response.BannerResponse
import com.illsang.management.dto.response.BannerUserResponse
import com.illsang.management.service.BannerService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springdoc.core.annotations.ParameterObject
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/banner")
@Tag(name = "Banner", description = "배너")
class BannerController(
    private val bannerService: BannerService
) {

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(operationId = "BAN001", summary= "배너 생성")
    fun createBanner(
        @RequestBody request: BannerCreateRequest,
    ): ResponseEntity<BannerResponse> {
        val banner = bannerService.createBanner(request)

        return ResponseEntity.status(HttpStatus.CREATED).body(
            BannerResponse.from(banner)
        )
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(operationId = "BAN002", summary= "배너 수정")
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
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(operationId = "BAN003", summary= "배너 삭제")
    fun deleteBanner(
        @PathVariable id: Long,
    ): ResponseEntity<Void> {
        bannerService.deleteBanner(id)

        return ResponseEntity.ok().build()
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(operationId = "BAN004", summary= "배너 검색 - 관리자용")
    fun findBySearch(
        @ParameterObject request: BannerSearchRequest,
        @ParameterObject @PageableDefault(size = 10) pageable: Pageable,
    ): ResponseEntity<Page<BannerResponse>> {
        val banners = this.bannerService.search(request, pageable)

        return ResponseEntity.ok(
            banners.map(BannerResponse::from)
        )
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER')")
    @Operation(operationId = "BAN005", summary= "배너 검색 - 사용자용")
    fun findBySearchForUser(
    ): ResponseEntity<List<BannerUserResponse>> {
        val banners = this.bannerService.searchForUser()

        return ResponseEntity.ok(
            banners.map(BannerUserResponse::from)
        )
    }

}
