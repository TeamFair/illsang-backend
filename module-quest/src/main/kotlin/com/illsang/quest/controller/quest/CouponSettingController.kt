package com.illsang.quest.controller.quest

import com.illsang.quest.dto.request.quest.CouponSettingCreateRequest
import com.illsang.quest.dto.request.quest.CouponSettingUpdateRequest
import com.illsang.quest.dto.response.quest.CouponSettingResponse
import com.illsang.quest.service.quest.CouponSettingService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/coupon/setting")
@Tag(name="Coupon Setting", description = "쿠폰 셋팅")
class CouponSettingController(
    private val couponSettingService: CouponSettingService,
) {


    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(operationId = "CPS001", summary = "쿠폰 셋팅 전체 조회")
    fun selectAllCouponSetting(): ResponseEntity<List<CouponSettingResponse>> {
        val settings = this.couponSettingService.getAll()
        return ResponseEntity.ok(settings.map(CouponSettingResponse::from))
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(operationId = "CPS002", summary = "쿠폰 셋팅 단일 조회")
    fun selectCouponSetting(
        @PathVariable id: Long,
    ): ResponseEntity<CouponSettingResponse> {
        val setting = this.couponSettingService.get(id)
        return ResponseEntity.ok(CouponSettingResponse.from(setting))
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(operationId = "CPS003", summary = "쿠폰 셋팅 생성")
    fun createCouponSetting(
        @RequestBody request: CouponSettingCreateRequest,
    ): ResponseEntity<CouponSettingResponse> {
        val setting = this.couponSettingService.create(request)
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(CouponSettingResponse.from(setting))
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(operationId = "CPS004", summary = "쿠폰 셋팅 수정")
    fun updateCouponSetting(
        @PathVariable id: Long,
        @RequestBody request: CouponSettingUpdateRequest,
    ): ResponseEntity<CouponSettingResponse> {
        val setting = this.couponSettingService.update(id, request)
        return ResponseEntity.ok(CouponSettingResponse.from(setting))
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(operationId = "CPS005", summary = "쿠폰 셋팅 삭제")
    fun deleteCouponSetting(
        @PathVariable id: Long,
    ): ResponseEntity<Void> {
        this.couponSettingService.deleteById(id)
        return ResponseEntity.ok().build()
    }
}