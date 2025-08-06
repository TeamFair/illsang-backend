package com.illsang.user.controller

import com.illsang.user.dto.response.CommercialRankResponse
import com.illsang.user.dto.response.MetroRankResponse
import com.illsang.user.dto.response.UserRankResponse
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
@RequestMapping("/api/v1/rank")
@Tag(name = "Rank", description = "랭킹")
class UserRankController(
    private val userPointService: UserPointService,
) {

    @GetMapping("/total")
    @PreAuthorize("hasRole('USER')")
    @Operation(operationId = "RAN001", summary = "포인트 합산 랭킹 (일상지역 + 일상존 + 기여도)")
    fun getTotalRank(
        @RequestParam commercialAreaCode: String,
        @ParameterObject @PageableDefault pageable: Pageable,
    ): ResponseEntity<Page<UserRankResponse>> {
        return ResponseEntity.ok(
            this.userPointService.findAllTotalRank(commercialAreaCode, pageable)
        )
    }

    @GetMapping("/metro")
    @PreAuthorize("hasRole('USER')")
    @Operation(operationId = "RAN002", summary = "일상지역 랭킹")
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
    @Operation(operationId = "RAN003", summary = "일상존 랭킹")
    fun getCommercialAreaRank(
        @RequestParam seasonId: Long? = null,
        @ParameterObject @PageableDefault pageable: Pageable,
    ): ResponseEntity<Page<CommercialRankResponse>> {
        return ResponseEntity.ok(
            this.userPointService.findAllRankByCommercialArea(seasonId, pageable)
        )
    }

    @GetMapping("/contribution")
    @PreAuthorize("hasRole('USER')")
    @Operation(operationId = "RAN004", summary = "기여도 랭킹")
    fun getContributionRank(
        @RequestParam seasonId: Long? = null,
        @ParameterObject @PageableDefault pageable: Pageable,
    ): ResponseEntity<Page<UserRankResponse>> {
        return ResponseEntity.ok(
            this.userPointService.findAllRankByContribution(seasonId, pageable)
        )
    }

}
