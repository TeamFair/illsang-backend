package com.illsang.management.controller

import com.illsang.management.dto.request.CommercialCreateRequest
import com.illsang.management.dto.request.CommercialUpdateRequest
import com.illsang.management.dto.response.CommercialAreaResponse
import com.illsang.management.service.AreaCommercialService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/area/commercial")
@Tag(name = "Area Commercial", description = "일상존 (상권지역)")
class AreaCommercialController(
    private val areaCommercialService: AreaCommercialService,
) {

    @GetMapping("/{commercialAreaCode}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @Operation(operationId = "ARC001", summary = "상권 지역 단일 조회")
    fun findCommercial(
        @PathVariable commercialAreaCode: String,
    ): ResponseEntity<CommercialAreaResponse> {
        val metro = this.areaCommercialService.findCommercialBy(commercialAreaCode)

        return ResponseEntity.ok(CommercialAreaResponse.from(metro))
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    @Operation(operationId = "ARC002", summary = "상권 지역 생성")
    fun createCommercial(
        @RequestBody request: CommercialCreateRequest,
    ): ResponseEntity<CommercialAreaResponse> {
        val commercial = this.areaCommercialService.createCommercial(request)

        return ResponseEntity.ok(CommercialAreaResponse.from(commercial))
    }

    @PutMapping("/{commercialAreaCode}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @Operation(operationId = "ARC003", summary = "상권 지역 수정")
    fun updateCommercial(
        @PathVariable commercialAreaCode: String,
        @RequestBody request: CommercialUpdateRequest,
    ): ResponseEntity<CommercialAreaResponse> {
        val metro = this.areaCommercialService.updateCommercial(commercialAreaCode, request)

        return ResponseEntity.ok(CommercialAreaResponse.from(metro))
    }

    @DeleteMapping("/{commercialAreaCode}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @Operation(operationId = "ARC004", summary = "상권 지역 삭제")
    fun deleteCommercial(
        @PathVariable commercialAreaCode: String,
    ): ResponseEntity<Void> {
        this.areaCommercialService.deleteCommercial(commercialAreaCode)

        return ResponseEntity.ok().build()
    }

}
