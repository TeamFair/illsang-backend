package com.illsang.quest.controller

import com.illsang.auth.domain.model.AuthenticationModel
import com.illsang.quest.dto.request.history.ChallengeCreateRequest
import com.illsang.quest.dto.response.history.ChallengeResponse
import com.illsang.quest.service.history.MissionHistoryService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/vi/challenge")
@Tag(name = "Challenge", description = "미션 도전")
class ChallengeController(
    private val missionHistoryService: MissionHistoryService,
) {

    @PostMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    @Operation(operationId = "CHA001", summary = "사용자 미션 제출")
    fun submitUserMission(
        @PathVariable id: Long,
        @RequestBody request: ChallengeCreateRequest,
        @AuthenticationPrincipal authenticationModel: AuthenticationModel,
    ): ResponseEntity<ChallengeResponse> {
        val challenge = this.missionHistoryService.submitMission(id, request, authenticationModel)

        return ResponseEntity.ok(
            ChallengeResponse.from(challenge)
        )
    }

}
