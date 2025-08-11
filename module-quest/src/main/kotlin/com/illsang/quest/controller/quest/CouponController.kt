package com.illsang.quest.controller.quest

import com.illsang.quest.dto.request.quest.CouponResponse
import com.illsang.quest.dto.request.quest.CreateCouponRequest
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/vi/coupon")
@Tag(name = "Coupon", description = "쿠폰")
class CouponController() {

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "쿠폰 생성")
    fun createCoupon(
        @RequestBody request: CreateCouponRequest
    ): ResponseEntity<CouponResponse> {
        // TODO: 생성 로직 구현 및 생성된 쿠폰 반환
        throw NotImplementedError("createCoupon 구현 필요")
    }

    @GetMapping("/list/{merchandiseId}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "소상공인 별 쿠폰 조회")
    fun getCouponByMerchandise(
            @PathVariable merchandiseId :Long
    ): ResponseEntity<List<CouponResponse>> {
        // TODO: 페이징/정렬 등 반영하여 리스트 반환
        throw NotImplementedError("listCoupons 구현 필요")
    }

    @GetMapping("/{couponId}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "쿠폰 단일 조회")
    fun getCoupon(
        @PathVariable couponId: Long
    ): ResponseEntity<CouponResponse> {
        // TODO: ID로 쿠폰 조회 후 반환
        throw NotImplementedError("getCoupon 구현 필요")
    }

}
