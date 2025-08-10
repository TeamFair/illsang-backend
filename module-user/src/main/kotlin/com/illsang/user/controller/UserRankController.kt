package com.illsang.user.controller

import com.illsang.auth.domain.model.AuthenticationModel
import com.illsang.common.enums.PointType
import com.illsang.user.dto.response.UserRankListResponse
import com.illsang.user.dto.response.UserRankResponse
import com.illsang.user.service.UserPointService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/rank/user")
@Tag(name = "Rank User", description = "사용자 랭킹")
class UserRankController(
    private val userPointService: UserPointService,
) {

    @GetMapping("/total")
    @PreAuthorize("hasRole('USER')")
    @Operation(operationId = "RAU001", summary = "포인트 합산 랭킹 (일상지역 + 일상존 + 기여도)")
    fun getUserTotalRank(
        @RequestParam commercialAreaCode: String,
    ): ResponseEntity<List<UserRankResponse>> {
        val userTotalRank = this.userPointService.findAllUserTotalRank(commercialAreaCode)

        return ResponseEntity.ok(
            userTotalRank.map { UserRankResponse.from(it) }
        )
    }

    @GetMapping("/contribution")
    @PreAuthorize("hasRole('USER')")
    @Operation(operationId = "RAU002", summary = "기여도 종합 랭킹")
    fun getUserContributionRank(
        @RequestParam seasonId: Long? = null,
    ): ResponseEntity<List<UserRankResponse>> {
        val userTotalRank = this.userPointService.findAllRankByUserContribution(seasonId)

        return ResponseEntity.ok(
            userTotalRank.map { UserRankResponse.from(it) }
        )
    }

    @GetMapping("/metro")
    @PreAuthorize("hasRole('USER')")
    @Operation(operationId = "RAU003", summary = "일상지역 사용자 전체 랭킹")
    fun getUserMetroRank(
        @RequestParam seasonId: Long? = null,
        @RequestParam metroAreaCode: String,
        @AuthenticationPrincipal auth: AuthenticationModel,
    ): ResponseEntity<UserRankListResponse> {
        val userTotalRank = this.userPointService.findAllRankByUserMetro(seasonId, metroAreaCode)
        val userRank = this.userPointService.findRankByUser(seasonId, metroAreaCode, PointType.METRO, auth.userId)

        return ResponseEntity.ok(
            UserRankListResponse(
                ranks = userTotalRank.map { UserRankResponse.from(it) },
                user = UserRankResponse.from(userRank),
            )
        )
    }

    @GetMapping("/commercial")
    @PreAuthorize("hasRole('USER')")
    @Operation(operationId = "RAU004", summary = "일상존 사용자 전체 랭킹")
    fun getUserCommercialRank(
        @RequestParam seasonId: Long? = null,
        @RequestParam commercialAreaCode: String,
        @AuthenticationPrincipal auth: AuthenticationModel,
    ): ResponseEntity<UserRankListResponse> {
        val userTotalRank = this.userPointService.findAllRankByUserCommercial(seasonId, commercialAreaCode)
        val userRank =
            this.userPointService.findRankByUser(seasonId, commercialAreaCode, PointType.COMMERCIAL, auth.userId)

        return ResponseEntity.ok(
            UserRankListResponse(
                ranks = userTotalRank.map { UserRankResponse.from(it) },
                user = UserRankResponse.from(userRank),
            )
        )
    }

}
