package com.illsang.quest.controller.quest

import com.illsang.quest.dto.request.quest.QuizAnswerCreateRequest
import com.illsang.quest.dto.request.quest.QuizAnswerUpdateRequest
import com.illsang.quest.dto.response.quest.QuizAnswerResponse
import com.illsang.quest.service.quest.QuizAnswerService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/quiz/answer")
@Tag(name = "Quiz Answer", description = "퀴즈 정답")
class QuizAnswerController(
    private val quizAnswerService: QuizAnswerService
) {

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(operationId = "ANS001", summary= "퀴즈 정답 생성")
    fun createQuizAnswer(
        @RequestBody request: QuizAnswerCreateRequest,
    ): ResponseEntity<QuizAnswerResponse> {
        val quizAnswer = this.quizAnswerService.createQuizAnswer(request)

        return ResponseEntity.status(HttpStatus.CREATED).body(
            QuizAnswerResponse.from(quizAnswer)
        )
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(operationId = "ANS002", summary= "퀴즈 정답 단일 조회")
    fun getQuizAnswer(
        @PathVariable id: Long,
    ): ResponseEntity<QuizAnswerResponse> {
        val quizAnswer = this.quizAnswerService.getQuizAnswerById(id)

        return ResponseEntity.ok(
            QuizAnswerResponse.from(quizAnswer)
        )
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(operationId = "ANS003", summary= "퀴즈 정답 수정")
    fun updateQuizAnswer(
        @PathVariable id: Long,
        @RequestBody request: QuizAnswerUpdateRequest,
    ): ResponseEntity<QuizAnswerResponse> {
        val quizAnswer = this.quizAnswerService.updateQuizAnswer(id, request)

        return ResponseEntity.ok(
            QuizAnswerResponse.from(quizAnswer)
        )
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(operationId = "ANS004", summary= "퀴즈 정답 삭제")
    fun deleteQuizAnswer(
        @PathVariable id: Long,
    ): ResponseEntity<Void> {
        this.quizAnswerService.deleteQuizAnswer(id)

        return ResponseEntity.ok().build()
    }
}
