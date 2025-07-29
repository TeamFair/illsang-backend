package com.illsang.management.controller

import com.illsang.management.dto.request.SeasonCreateRequest
import com.illsang.management.dto.request.SeasonUpdateRequest
import com.illsang.management.dto.response.SeasonResponse
import com.illsang.management.service.SeasonService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/season")
@Tag(name = "Season", description = "시즌")
class SeasonController(
    private val seasonService: SeasonService,
) {

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Operation(operationId = "SEA001", summary = "시즌 전체 조회")
    fun findAll(): ResponseEntity<List<SeasonResponse>> {
        val seasons = this.seasonService.findAll()

        return ResponseEntity.ok(
            seasons.map(SeasonResponse::from)
        )
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(operationId = "SEA002", summary = "시즌 생성")
    fun createSeason(
        @RequestBody request: SeasonCreateRequest,
    ): ResponseEntity<SeasonResponse> {
        val season = this.seasonService.createSeason(request)

        return ResponseEntity.ok(
            SeasonResponse.from(season)
        )
    }

    @PostMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(operationId = "SEA003", summary = "시즌 수정")
    fun updateSeason(
        @PathVariable id: Long,
        @RequestBody request: SeasonUpdateRequest,
    ): ResponseEntity<SeasonResponse> {
        val season = this.seasonService.updateSeason(id, request)

        return ResponseEntity.ok(
            SeasonResponse.from(season)
        )
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(operationId = "SEA004", summary = "시즌 삭제")
    fun deleteSeason(
        @PathVariable id: Long,
    ): ResponseEntity<Void> {
        this.seasonService.deleteSeason(id)

        return ResponseEntity.ok().build()
    }

}
