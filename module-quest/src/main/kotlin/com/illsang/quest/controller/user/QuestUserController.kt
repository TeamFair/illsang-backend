package com.illsang.quest.controller.user

import com.illsang.auth.domain.model.AuthenticationModel
import com.illsang.quest.dto.request.quest.QuestUserBannerRequest
import com.illsang.quest.dto.request.quest.QuestUserTypeRequest
import com.illsang.quest.dto.response.user.*
import com.illsang.quest.service.quest.QuestUserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springdoc.core.annotations.ParameterObject
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/quest/user")
@Tag(name = "Quest Search", description = "퀘스트 사용자 노출")
class QuestUserController(
    private val questUserService: QuestUserService,
) {

    @GetMapping("/search/popular")
    @PreAuthorize("hasRole('USER')")
    @Operation(operationId = "QUS001", summary = "미완료 인기퀘스트 조회")
    fun selectAllPopular(
        @RequestParam commercialAreaCode: String,
        @AuthenticationPrincipal authenticationModel: AuthenticationModel,
        @ParameterObject @PageableDefault(size = 10) pageable: Pageable,
    ): ResponseEntity<Page<QuestUserPopularResponse>> {
        val quests = this.questUserService.findAllPopular(authenticationModel.userId, commercialAreaCode, pageable)

        return ResponseEntity.ok(
            quests
        )
    }

    @GetMapping("/search/recommend")
    @PreAuthorize("hasRole('USER')")
    @Operation(operationId = "QUS002", summary = "미완료 추천퀘스트 조회")
    fun selectAllRecommend(
        @RequestParam commercialAreaCode: String,
        @AuthenticationPrincipal authenticationModel: AuthenticationModel,
        @ParameterObject @PageableDefault(size = 10) pageable: Pageable,
    ): ResponseEntity<Page<QuestUserRecommendResponse>> {
        val quests = this.questUserService.findAllRecommend(authenticationModel.userId, commercialAreaCode, pageable)

        return ResponseEntity.ok(
            quests
        )
    }

    @GetMapping("/search/reward")
    @PreAuthorize("hasRole('USER')")
    @Operation(operationId = "QUS003", summary = "미완료 큰 보상 퀘스트 조회")
    fun selectAllReward(
        @RequestParam commercialAreaCode: String,
        @AuthenticationPrincipal authenticationModel: AuthenticationModel,
        @ParameterObject @PageableDefault(size = 10) pageable: Pageable,
    ): ResponseEntity<Page<QuestUserRewardResponse>> {
        val quests = this.questUserService.findAllReward(authenticationModel.userId, commercialAreaCode, pageable)

        return ResponseEntity.ok(
            quests
        )
    }

    @GetMapping("/search/type")
    @PreAuthorize("hasRole('USER')")
    @Operation(operationId = "QUS004", summary = "퀘스트 유형별 조회")
    fun selectAllType(
        @RequestParam commercialAreaCode: String,
        @ParameterObject request: QuestUserTypeRequest,
        @AuthenticationPrincipal authenticationModel: AuthenticationModel,
        @ParameterObject @PageableDefault(size = 10) pageable: Pageable,
    ): ResponseEntity<Page<QuestUserTypeResponse>> {
        val quests = this.questUserService.findAllType(authenticationModel.userId, commercialAreaCode, request, pageable)

        return ResponseEntity.ok(
            quests
        )
    }

    @GetMapping("/search/banner/{bannerId}")
    @PreAuthorize("hasRole('USER')")
    @Operation(operationId = "QUS004", summary = "퀘스트 유형별 조회")
    fun selectAllBanner(
        @PathVariable bannerId: Long,
        @ParameterObject request: QuestUserBannerRequest,
        @AuthenticationPrincipal authenticationModel: AuthenticationModel,
        @ParameterObject @PageableDefault(size = 10) pageable: Pageable,
    ): ResponseEntity<Page<QuestUserBannerResponse>> {
        val quests = this.questUserService.findAllBanner(authenticationModel.userId, bannerId, request, pageable)

        return ResponseEntity.ok(
            quests
        )
    }

    @GetMapping("/{questId}")
    @PreAuthorize("hasRole('USER')")
    @Operation(operationId = "QUS006", summary = "퀘스트 상세 정보 조회")
    fun selectQuestDetail(
        @PathVariable questId: Long,
        @AuthenticationPrincipal authenticationModel: AuthenticationModel,
    ): ResponseEntity<QuestUserDetailResponse> {
        val quests = this.questUserService.findQuestDetail(authenticationModel.userId, questId)

        return ResponseEntity.ok(
            quests
        )
    }

}
