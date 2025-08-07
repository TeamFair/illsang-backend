package com.illsang.user.controller

import com.illsang.user.dto.response.CommercialRankResponse
import com.illsang.user.dto.response.MetroRankResponse
import com.illsang.user.service.UserPointService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springdoc.core.annotations.ParameterObject
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/rank/area")
@Tag(name = "Rank Area", description = "일상지역 / 일상 존 랭킹")
class AreaRankController(
    private val userPointService: UserPointService,
) {

    @GetMapping("/metro")
    @PreAuthorize("hasRole('USER')")
    @Operation(operationId = "RAA001", summary = "일상지역 종합 랭킹")
    fun getMetroAreaRank(
        @RequestParam seasonId: Long? = null,
        @ParameterObject @PageableDefault pageable: Pageable,
    ): ResponseEntity<Page<MetroRankResponse>> {
        return ResponseEntity.ok(
            this.userPointService.findAllRankByMetroArea(seasonId, pageable)
        )
    }

    @GetMapping("/commercial")
    @PreAuthorize("hasRole('USER')")
    @Operation(operationId = "RAA002", summary = "일상존 종합 랭킹")
    fun getCommercialAreaRank(
        @RequestParam seasonId: Long? = null,
        @ParameterObject @PageableDefault pageable: Pageable,
    ): ResponseEntity<Page<CommercialRankResponse>> {
        return ResponseEntity.ok(
            this.userPointService.findAllRankByCommercialArea(seasonId, pageable)
        )
    }

}
