package com.illsang.quest.controller.quest


import com.illsang.quest.dto.request.quest.CouponCreateRequest
import com.illsang.quest.dto.request.quest.CouponUpdateRequest
import com.illsang.quest.dto.response.quest.CouponResponse
import com.illsang.quest.service.quest.CouponService
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
@RequestMapping("/api/vi/coupon")
@Tag(name = "Coupon", description = "쿠폰")
class CouponController(
    private val couponService: CouponService

) {

    @PostMapping()
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(operationId = "COP001", summary = "쿠폰 생성")
    fun create(@RequestBody request: CouponCreateRequest): ResponseEntity<CouponResponse> {
        val model = couponService.create(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(CouponResponse.from(model))
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(operationId = "COP002", summary = "쿠폰 상세 조회")
    fun getById(@PathVariable id: Long): ResponseEntity<CouponResponse> {
        val model = couponService.getById(id)
        return ResponseEntity.ok(CouponResponse.from(model))
    }

    @GetMapping("/store/{storeId}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(operationId = "COP003", summary = "소상공인 ID 별 리스트조회")
    fun listByStore(@PathVariable storeId: Long): ResponseEntity<List<CouponResponse>> {
        val models = couponService.listByStore(storeId)
        return ResponseEntity.ok(models.map(CouponResponse::from))
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(operationId = "COP004", summary = "쿠폰 수정")
    fun update(
        @PathVariable id: Long,
        @RequestBody request: CouponUpdateRequest
    ): ResponseEntity<CouponResponse> {
        val model = couponService.update(id, request)
        return ResponseEntity.ok(CouponResponse.from(model))
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(operationId = "COP005", summary = "쿠폰 삭제")
    fun delete(@PathVariable id: Long): ResponseEntity<Void> {
        couponService.delete(id)
        return ResponseEntity.noContent().build()
    }
}
