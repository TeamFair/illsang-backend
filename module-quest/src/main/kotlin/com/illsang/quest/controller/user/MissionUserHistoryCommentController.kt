package com.illsang.quest.controller.user

import com.illsang.quest.dto.request.user.MissionHistoryCommentRequest
import com.illsang.quest.dto.request.user.MissionHistoryCommentUpdateRequest
import com.illsang.quest.dto.response.user.MissionHistoryCommentResponse
import com.illsang.quest.service.user.MissionHistoryCommentService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springdoc.core.annotations.ParameterObject
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/mission/user/history/comment")
@Tag(name = "Mission User History Comment", description = "사용자 미션 이력 댓글")
class MissionUserHistoryCommentController (
    private val missionHistoryCommentService: MissionHistoryCommentService,
    ){

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    @Operation(operationId = "MIC001", summary = "댓글 조회")
    fun selectAllByUserMissionHistoryId(
        @ParameterObject missionHistoryId: Long,
    ): ResponseEntity<List<MissionHistoryCommentResponse>> {

        return  ResponseEntity.ok(
            this.missionHistoryCommentService.getMissionHistoryComment(missionHistoryId)
        )
    }

    @PostMapping("/{missionHistoryId}")
    @PreAuthorize("hasRole('USER')")
    @Operation(operationId = "MIC002", summary = "댓글 생성")
    fun createMissionHistoryComment(
        @PathVariable missionHistoryId: Long,
        @RequestBody request: MissionHistoryCommentRequest,
    ): ResponseEntity<Void> {
        this.missionHistoryCommentService.createComment(request, missionHistoryId)
        return ResponseEntity.ok().build()
    }

    @DeleteMapping("/{commentId}")
    @PreAuthorize("hasRole('USER')")
    @Operation(operationId = "MIC003", summary = "댓글 삭제")
    fun deleteMissionHistoryComment(
        @PathVariable commentId: Long,
    ): ResponseEntity<Void> {
        this.missionHistoryCommentService.deleteComment(commentId)
        return ResponseEntity.ok().build()
    }

    @PutMapping("/{commentId}")
    @PreAuthorize("hasRole('USER')")
    @Operation(operationId = "MIC004", summary = "댓글 수정")
    fun updateMissionHistoryComment(
        @PathVariable commentId: Long,
        @RequestBody request: MissionHistoryCommentUpdateRequest,
    ): ResponseEntity<Void> {
        this.missionHistoryCommentService.updateComment(commentId, request)
        return ResponseEntity.ok().build()
    }
}