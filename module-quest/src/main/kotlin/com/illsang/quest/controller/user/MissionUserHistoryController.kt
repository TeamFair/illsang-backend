package com.illsang.quest.controller.user

import com.illsang.auth.domain.model.AuthenticationModel
import com.illsang.quest.dto.request.user.MissionHistoryEmojiCreateRequest
import com.illsang.quest.dto.response.user.MissionHistoryExampleResponse
import com.illsang.quest.dto.response.user.MissionHistoryOwnerResponse
import com.illsang.quest.dto.response.user.MissionHistoryRandomResponse
import com.illsang.quest.enums.EmojiType
import com.illsang.quest.service.user.MissionHistoryService
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
@RequestMapping("/api/v1/mission/user/history")
@Tag(name = "Mission User History", description = "사용자 미션 이력")
class MissionUserHistoryController(
    private val missionHistoryService: MissionHistoryService,
) {

    @GetMapping("/random")
    @PreAuthorize("hasRole('USER')")
    @Operation(operationId = "MIU001", summary = "랜덤 이력 조회")
    fun selectAllRandom(
        @ParameterObject @PageableDefault(size = 10) pageable: Pageable,
        @AuthenticationPrincipal authenticationModel: AuthenticationModel,
    ): ResponseEntity<Page<MissionHistoryRandomResponse>> {
        val missionHistories = this.missionHistoryService.findAllRandom(authenticationModel.userId, pageable)

        return ResponseEntity.ok(missionHistories)
    }

    @PostMapping("/{missionHistoryId}/view-count")
    @PreAuthorize("hasRole('USER')")
    @Operation(operationId = "MIU002", summary = "조회수 증가")
    fun increaseViewCount(
        @PathVariable missionHistoryId: Long,
        @AuthenticationPrincipal authenticationModel: AuthenticationModel,
    ): ResponseEntity<Void> {
        this.missionHistoryService.increaseViewCount(missionHistoryId, authenticationModel.userId)

        return ResponseEntity.ok().build()
    }

    @PostMapping("/{missionHistoryId}/emoji")
    @PreAuthorize("hasRole('USER')")
    @Operation(operationId = "MIU003", summary = "이모지 등록")
    fun createEmoji(
        @PathVariable missionHistoryId: Long,
        @RequestBody request: MissionHistoryEmojiCreateRequest,
        @AuthenticationPrincipal authenticationModel: AuthenticationModel,
    ): ResponseEntity<Void> {
        this.missionHistoryService.createEmoji(missionHistoryId, authenticationModel.userId, request.emojiType)

        return ResponseEntity.ok().build()
    }

    @DeleteMapping("/{missionHistoryId}/emoji")
    @PreAuthorize("hasRole('USER')")
    @Operation(operationId = "MIU004", summary = "이모지 해제")
    fun deleteEmoji(
        @PathVariable missionHistoryId: Long,
        @RequestParam emojiType: EmojiType,
        @AuthenticationPrincipal authenticationModel: AuthenticationModel,
    ): ResponseEntity<Void> {
        this.missionHistoryService.deleteEmoji(missionHistoryId, authenticationModel.userId, emojiType)

        return ResponseEntity.ok().build()
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    @Operation(operationId = "MIU005", summary = "수행한 퀘스트 이력")
    fun selectAllOwner(
        @ParameterObject @PageableDefault(size = 10) pageable: Pageable,
        @RequestParam(required = false) userId: String?,
        @AuthenticationPrincipal authenticationModel: AuthenticationModel,
    ): ResponseEntity<Page<MissionHistoryOwnerResponse>> {
        val userId = userId ?: authenticationModel.userId
        val missionHistories = this.missionHistoryService.findByUserId(userId, pageable)

        return ResponseEntity.ok(missionHistories)
    }

    @PutMapping("/{missionHistoryId}")
    @PreAuthorize("hasRole('USER')")
    @Operation(operationId = "MIU006", summary = "신고하기")
    fun reportMissionHistory(
        @PathVariable missionHistoryId: Long,
    ): ResponseEntity<Void> {
        this.missionHistoryService.reportMissionHistory(missionHistoryId)

        return ResponseEntity.ok().build()
    }

    @DeleteMapping("/{missionHistoryId}")
    @PreAuthorize("hasRole('USER')")
    @Operation(operationId = "MIU007", summary = "수행한 이력 삭제")
    fun deleteMissionHistory(
        @PathVariable missionHistoryId: Long,
        @AuthenticationPrincipal authenticationModel: AuthenticationModel,
    ): ResponseEntity<Void> {
        this.missionHistoryService.deleteMissionHistory(missionHistoryId, authenticationModel.userId)

        return ResponseEntity.ok().build()
    }

    @GetMapping("/example")
    @PreAuthorize("hasRole('USER')")
    @Operation(operationId = "MIU008", summary = "미션 수행 예시")
    fun exampleMissionHistory(
        @RequestParam missionId: Long,
        @ParameterObject @PageableDefault(size = 10) pageable: Pageable,
        @AuthenticationPrincipal authenticationModel: AuthenticationModel,
    ): ResponseEntity<Page<MissionHistoryExampleResponse>> {
        return ResponseEntity.ok(
            this.missionHistoryService.exampleMissionHistory(missionId, authenticationModel.userId, pageable)
        )
    }

}
