package com.illsang.management.controller

import com.illsang.management.dto.response.MetroAreaResponse
import com.illsang.management.service.AreaService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/area")
@Tag(name = "Area", description = "지역")
class AreaController(
    private val areaService: AreaService,
) {

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Operation(operationId = "ARE001", summary = "광역 지역 전체 조회 (상권 지역 포함)")
    fun findAllMetro(): ResponseEntity<List<MetroAreaResponse>> {
        val metros = this.areaService.findAllMetro()

        return ResponseEntity.ok(
            metros.map(MetroAreaResponse::from)
        )
    }

}
