package com.illsang.quest.controller

import com.illsang.auth.domain.model.AuthenticationModel
import com.illsang.quest.dto.response.quest.QuestUserPopularResponse
import com.illsang.quest.dto.response.quest.QuestUserRecommendResponse
import com.illsang.quest.dto.response.quest.QuestUserRewardResponse
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
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/quest/user")
@Tag(name = "Quest User", description = "퀘스트 사용자 노출")
class QuestUserController(
    private val questUserService: QuestUserService,
) {

    @GetMapping("/popular")
    @PreAuthorize("hasRole('USER')")
    @Operation(operationId = "QUU001", summary = "미완료 인기퀘스트 조회")
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

    @GetMapping("/recommend")
    @PreAuthorize("hasRole('USER')")
    @Operation(operationId = "QUU002", summary = "미완료 추천퀘스트 조회")
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

    @GetMapping("/reward")
    @PreAuthorize("hasRole('USER')")
    @Operation(operationId = "QUU003", summary = "미완료 큰 보상 퀘스트 조회")
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

}
