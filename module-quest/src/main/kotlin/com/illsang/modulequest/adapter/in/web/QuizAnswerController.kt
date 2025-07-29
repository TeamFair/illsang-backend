package com.illsang.modulequest.adapter.`in`.web

import com.illsang.common.enums.ResponseMsg
import com.illsang.modulequest.adapter.`in`.web.model.request.CreateQuizAnswerRequest
import com.illsang.modulequest.adapter.`in`.web.model.request.UpdateQuizAnswerRequest
import com.illsang.modulequest.adapter.`in`.web.model.response.QuizAnswerResponse
import com.illsang.modulequest.application.command.CreateQuizAnswerCommand
import com.illsang.modulequest.application.command.UpdateQuizAnswerCommand
import com.illsang.modulequest.application.service.QuizAnswerService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/quiz-answers")
class QuizAnswerController(
    private val quizAnswerService: QuizAnswerService
) {

    @PostMapping
    fun createQuizAnswer(@RequestBody request: CreateQuizAnswerRequest): ResponseEntity<QuizAnswerResponse> {
        val command = CreateQuizAnswerCommand(
            answer = request.answer,
            sortOrder = request.sortOrder,
            quizId = request.quizId
        )
        val quizAnswer = quizAnswerService.createQuizAnswer(command)
        return ResponseEntity.status(HttpStatus.CREATED).body(QuizAnswerResponse.from(quizAnswer))
    }

    @GetMapping("/{id}")
    fun getQuizAnswer(@PathVariable id: Long): ResponseEntity<QuizAnswerResponse> {
        val quizAnswer = quizAnswerService.getQuizAnswerById(id)
        return if (quizAnswer != null) {
            ResponseEntity.ok(QuizAnswerResponse.from(quizAnswer))
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @GetMapping
    fun getAllQuizAnswers(): ResponseEntity<List<QuizAnswerResponse>> {
        val quizAnswers = quizAnswerService.getAllQuizAnswers()
        return ResponseEntity.ok(quizAnswers.map { QuizAnswerResponse.from(it) })
    }

    @GetMapping("/quiz/{quizId}")
    fun getQuizAnswersByQuizId(@PathVariable quizId: Long): ResponseEntity<List<QuizAnswerResponse>> {
        val quizAnswers = quizAnswerService.getQuizAnswersByQuizId(quizId)
        return ResponseEntity.ok(quizAnswers.map { QuizAnswerResponse.from(it) })
    }

    @PutMapping("/{id}")
    fun updateQuizAnswer(
        @PathVariable id: Long,
        @RequestBody request: UpdateQuizAnswerRequest
    ): ResponseEntity<QuizAnswerResponse> {
        val command = UpdateQuizAnswerCommand(
            id = id,
            answer = request.answer,
            sortOrder = request.sortOrder
        )
        val quizAnswer = quizAnswerService.updateQuizAnswer(command)
        return if (quizAnswer != null) {
            ResponseEntity.ok(QuizAnswerResponse.from(quizAnswer))
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/{id}")
    fun deleteQuizAnswer(@PathVariable id: Long): ResponseEntity<ResponseMsg> {
        val deleted = quizAnswerService.deleteQuizAnswer(id)
        return if (deleted) {
            ResponseEntity.ok(ResponseMsg.SUCCESS)
        } else {
            ResponseEntity.notFound().build()
        }
    }
}
