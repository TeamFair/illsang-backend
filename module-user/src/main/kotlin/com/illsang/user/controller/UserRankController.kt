package com.illsang.user.controller

import com.illsang.user.dto.response.UserRankTotalResponse
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
@RequestMapping("/api/v1/user/rank")
@Tag(name = "User Rank", description = "사용자 랭킹")
class UserRankController(
    private val userPointService: UserPointService,
) {

    @GetMapping("/total")
    @PreAuthorize("hasRole('USER')")
    @Operation(operationId = "USR001", summary = "사용자 랭킹 전체 조회 (포인트 합산)")
    fun getTotalRank(
        @RequestParam commercialAreaCode: String,
        @ParameterObject @PageableDefault pageable: Pageable,
    ): ResponseEntity<Page<UserRankTotalResponse>> {
        return ResponseEntity.ok(
            this.userPointService.findAllTotalRank(commercialAreaCode, pageable)
        )
    }

}
