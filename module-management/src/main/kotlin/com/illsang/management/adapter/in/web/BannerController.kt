package com.illsang.management.adapter.`in`.web

import com.illsang.common.enums.ResponseMsg
import com.illsang.management.application.command.CreateBannerCommand
import com.illsang.management.application.command.UpdateBannerCommand
import com.illsang.management.application.service.BannerService
import com.illsang.management.domain.model.BannerModel
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/banners")
class BannerController(
    private val bannerService: BannerService
) {

    /**
     * crete banner
     */
    @PostMapping
    fun createBanner(@RequestBody command: CreateBannerCommand): ResponseEntity<BannerModel> {
        val createdBanner = bannerService.createBanner(command)
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBanner)
    }

    /**
     * get banner
     */
    @GetMapping("/{id}")
    fun getBanner(@PathVariable id: Long): ResponseEntity<BannerModel> {
        val banner = bannerService.getBanner(id)
        return ResponseEntity.ok(banner)
    }

    /**
     * get all banners
     */
    @GetMapping
    fun getAllBanners(): ResponseEntity<List<BannerModel>> {
        val banners = bannerService.getAllBanners()
        return ResponseEntity.ok(banners)
    }

    /**
     * get all active banners
     */
    @GetMapping("/active")
    fun getActiveBanners(): ResponseEntity<List<BannerModel>> {
        val banners = bannerService.getActiveBanners()
        return ResponseEntity.ok(banners)
    }

    /**
     * update banner
     */
    @PutMapping("/{id}")
    fun updateBanner(
        @PathVariable id: Long,
        @RequestBody command: UpdateBannerCommand
    ): ResponseEntity<BannerModel> {
        val updatedCommand = command.copy(id = id)
        val updatedBanner = bannerService.updateBanner(updatedCommand)
        return ResponseEntity.ok(updatedBanner)
    }

    /**
     * delete banner
     */
    @DeleteMapping("/{id}")
    fun deleteBanner(@PathVariable id: Long): ResponseEntity<ResponseMsg> {
        bannerService.deleteBanner(id)
        return ResponseEntity.ok(ResponseMsg.SUCCESS)
    }
} 