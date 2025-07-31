package com.illsang.quest.controller

import com.illsang.quest.dto.request.quest.QuizCreateRequest
import com.illsang.quest.dto.request.quest.QuizUpdateRequest
import com.illsang.quest.dto.response.quest.QuizResponse
import com.illsang.quest.service.quest.QuizService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/quiz")
@Tag(name = "Quiz", description = "퀴즈")
class QuizController(
    private val quizService: QuizService
) {

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(operationId = "QUI001", summary= "퀴즈 생성")
    fun createQuiz(
        @RequestBody request: QuizCreateRequest,
    ): ResponseEntity<QuizResponse> {
        val quiz = this.quizService.createQuiz(request)

        return ResponseEntity.status(HttpStatus.CREATED).body(
            QuizResponse.from(quiz)
        )
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(operationId = "QUI002", summary= "퀴즈 단일 조회")
    fun getQuiz(@PathVariable id: Long): ResponseEntity<QuizResponse> {
        val quiz = this.quizService.getQuizById(id)

        return ResponseEntity.ok(
            QuizResponse.from(quiz)
        )
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(operationId = "QUI003", summary= "퀴즈 수정")
    fun updateQuiz(
        @PathVariable id: Long,
        @RequestBody request: QuizUpdateRequest,
    ): ResponseEntity<QuizResponse> {
        val quiz = this.quizService.updateQuiz(id, request)

        return ResponseEntity.ok(
            QuizResponse.from(quiz)
        )
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(operationId = "QUI004", summary= "퀴즈 석재")
    fun deleteQuiz(
        @PathVariable id: Long,
    ): ResponseEntity<Void> {
        this.quizService.deleteQuiz(id)

        return ResponseEntity.ok().build()
    }
}
