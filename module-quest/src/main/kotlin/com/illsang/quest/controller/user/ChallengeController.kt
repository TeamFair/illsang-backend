package com.illsang.quest.controller.user

import com.illsang.auth.domain.model.AuthenticationModel
import com.illsang.quest.dto.request.user.ChallengeCreateRequest
import com.illsang.quest.dto.response.user.ChallengeResponse
import com.illsang.quest.dto.response.user.RandomQuizResponse
import com.illsang.quest.service.quest.QuizService
import com.illsang.quest.service.user.MissionHistoryService
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
    private val quizService: QuizService,
    private val missionHistoryService: MissionHistoryService,
) {

    @PostMapping("/mission")
    @PreAuthorize("hasRole('USER')")
    @Operation(operationId = "CHA001", summary = "사용자 미션 제출")
    fun submitUserMission(
        @RequestBody request: ChallengeCreateRequest,
        @AuthenticationPrincipal authenticationModel: AuthenticationModel,
    ): ResponseEntity<ChallengeResponse> {
        val challenge = this.missionHistoryService.submitMission(request, authenticationModel)

        return ResponseEntity.ok(
            ChallengeResponse.from(challenge)
        )
    }

    @GetMapping("/random-quiz")
    @PreAuthorize("hasRole('USER')")
    @Operation(operationId = "CHA002", summary = "사용자 랜덤 퀴즈 조회")
    fun selectRandomQuiz(
        @RequestParam missionId: Long,
    ): ResponseEntity<RandomQuizResponse> {
        val quiz = this.quizService.findForRandom(missionId)

        return ResponseEntity.ok(
            RandomQuizResponse.from(quiz)
        )
    }

}
