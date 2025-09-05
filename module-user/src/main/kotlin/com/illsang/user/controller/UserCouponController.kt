package com.illsang.user.controller

import com.illsang.auth.domain.model.AuthenticationModel
import com.illsang.user.dto.request.CouponPasswordVerifyRequest
import com.illsang.user.dto.request.UserCouponCreateRequest
import com.illsang.user.dto.request.UserCouponUpdateRequest
import com.illsang.user.dto.response.CouponPasswordVerifyResponse
import com.illsang.user.dto.response.UserCouponResponse
import com.illsang.user.service.UserCouponService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springdoc.core.annotations.ParameterObject
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/user/coupon")
@Tag(name = "Coupon User", description = "쿠폰 사용자")
class UserCouponController(
    private val userCouponService: UserCouponService
) {

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER')")
    @Operation(operationId = "USC001", summary = "사용자 쿠폰 ID별 상세 조회")
    fun getById(@PathVariable id: Long): ResponseEntity<UserCouponResponse> {
        val model = userCouponService.getById(id)
        return ResponseEntity.ok(UserCouponResponse.from(model))
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER')")
    @Operation(operationId = "USC002", summary = "사용자 쿠폰 리스트 조회")
    fun listByUser(
        @AuthenticationPrincipal auth: AuthenticationModel,
        @ParameterObject @PageableDefault(size = 10) pageable: Pageable,
    ): ResponseEntity<Page<UserCouponResponse>> {
        val models = userCouponService.listByUser(auth.userId, pageable)
        return ResponseEntity.ok(models.map(UserCouponResponse::from))
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('USER')")
    @Operation(operationId = "USC003", summary = "사용자 쿠폰 생성")
    fun create(@RequestBody request: UserCouponCreateRequest): ResponseEntity<UserCouponResponse> {
        val model = userCouponService.create(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(UserCouponResponse.from(model))
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER')")
    @Operation(operationId = "USC004", summary = "사용자 쿠폰 수정")
    fun update(
        @PathVariable id: Long,
        @RequestBody request: UserCouponUpdateRequest
    ): ResponseEntity<UserCouponResponse> {
        val model = userCouponService.update(id, request)
        return ResponseEntity.ok(UserCouponResponse.from(model))
    }

    @PostMapping("/{id}/verify-password")
    @PreAuthorize("hasAnyRole('USER')")
    @Operation(operationId = "USC005", summary = "사용자 쿠폰 비밀번호 검증")
    fun verifyPassword(
        @PathVariable id: Long,
        @RequestBody request: CouponPasswordVerifyRequest
    ): ResponseEntity<CouponPasswordVerifyResponse> {
        userCouponService.verifyPassword(id, request.password)
        return ResponseEntity.ok(CouponPasswordVerifyResponse(true))
    }
}
