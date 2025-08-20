package com.illsang.management.controller

import com.illsang.management.dto.request.MetroCreateRequest
import com.illsang.management.dto.request.MetroUpdateRequest
import com.illsang.management.dto.response.MetroAreaResponse
import com.illsang.management.service.AreaMetroService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/area/metro")
@Tag(name = "Area Metro", description = "일상지역 (광역지역)")
class AreaMetroController(
    private val areaMetroService: AreaMetroService,
) {

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Operation(operationId = "ARM001", summary = "광역 지역 전체 조회 (상권 지역 포함)")
    fun findAllMetro(): ResponseEntity<List<MetroAreaResponse>> {
        val metros = this.areaMetroService.findAllMetro()

        return ResponseEntity.ok(
            metros.map(MetroAreaResponse::from)
        )
    }

    @GetMapping("/{metroAreaCode}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @Operation(operationId = "ARM002", summary = "광역 지역 단일 조회")
    fun findMetro(
        @PathVariable metroAreaCode: String,
    ): ResponseEntity<MetroAreaResponse> {
        val metro = this.areaMetroService.findMetroBy(metroAreaCode)

        return ResponseEntity.ok(MetroAreaResponse.from(metro))
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    @Operation(operationId = "ARM003", summary = "광역 지역 생성")
    fun createMetro(
        @RequestBody request: MetroCreateRequest,
    ): ResponseEntity<MetroAreaResponse> {
        val metro = this.areaMetroService.createMetro(request)

        return ResponseEntity.ok(MetroAreaResponse.from(metro))
    }

    @PutMapping("/{metroAreaCode}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @Operation(operationId = "ARM004", summary = "광역 지역 수정")
    fun updateMetro(
        @PathVariable metroAreaCode: String,
        @RequestBody request: MetroUpdateRequest,
    ): ResponseEntity<MetroAreaResponse> {
        val metro = this.areaMetroService.updateMetro(metroAreaCode, request)

        return ResponseEntity.ok(MetroAreaResponse.from(metro))
    }

    @DeleteMapping("/{metroAreaCode}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @Operation(operationId = "ARM005", summary = "광역 지역 삭제")
    fun deleteMetro(
        @PathVariable metroAreaCode: String,
    ): ResponseEntity<Void> {
        this.areaMetroService.deleteMetro(metroAreaCode)

        return ResponseEntity.ok().build()
    }

}
