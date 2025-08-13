package com.illsang.user.controller

import com.illsang.user.dto.response.CouponUserResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/vi/coupon/user")
@Tag(name = "Coupon User", description = "쿠폰 사용자")
class CouponUserController() {


    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "쿠폰-유저 단일 조회")
    fun getCouponUser(
        @PathVariable id: Long
    ): ResponseEntity<CouponUserResponse> {
        // TODO: 단일 조회 구현
        throw NotImplementedError("getCouponUser 구현 필요")
    }



    @GetMapping("/by-user/{userId}")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "특정 유저의 쿠폰 리스트 조회")
    fun listByUser(
        @PathVariable userId: Long,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "20") size: Int
    ): ResponseEntity<List<CouponUserResponse>> {
        // TODO: 페이징/정렬 반영하여 목록 반환
        throw NotImplementedError("listByUser 구현 필요")
    }


}