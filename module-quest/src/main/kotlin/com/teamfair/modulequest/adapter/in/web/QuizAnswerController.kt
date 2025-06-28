package com.teamfair.modulequest.adapter.`in`.web

import com.teamfair.modulequest.application.port.`in`.QuizUseCase
import com.teamfair.modulequest.domain.model.QuizAnswer
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/quiz-answers")
class QuizAnswerController(private val quizUseCase: QuizUseCase) {

    @PostMapping("/quiz/{quizId}")
    fun createQuizAnswer(@PathVariable quizId: Long, @RequestBody answerRequest: QuizAnswerRequest): ResponseEntity<QuizAnswerResponse> {
        val quizAnswer = answerRequest.toDomain()
        val createdAnswer = quizUseCase.createQuizAnswer(quizAnswer, quizId)
        return ResponseEntity(QuizAnswerResponse.fromDomain(createdAnswer), HttpStatus.CREATED)
    }

    @GetMapping("/{id}")
    fun getQuizAnswer(@PathVariable id: Long): ResponseEntity<QuizAnswerResponse> {
        val answer = quizUseCase.getQuizAnswer(id)
        return ResponseEntity.ok(QuizAnswerResponse.fromDomain(answer))
    }

    @GetMapping("/quiz/{quizId}")
    fun getQuizAnswersByQuizId(@PathVariable quizId: Long): ResponseEntity<List<QuizAnswerResponse>> {
        val answers = quizUseCase.getQuizAnswersByQuizId(quizId)
        return ResponseEntity.ok(answers.map { QuizAnswerResponse.fromDomain(it) })
    }

    @PutMapping("/{id}")
    fun updateQuizAnswer(@PathVariable id: Long, @RequestBody answerRequest: QuizAnswerRequest): ResponseEntity<QuizAnswerResponse> {
        val quizAnswer = answerRequest.toDomain()
        val updatedAnswer = quizUseCase.updateQuizAnswer(id, quizAnswer)
        return ResponseEntity.ok(QuizAnswerResponse.fromDomain(updatedAnswer))
    }

    @DeleteMapping("/{id}")
    fun deleteQuizAnswer(@PathVariable id: Long): ResponseEntity<Void> {
        quizUseCase.deleteQuizAnswer(id)
        return ResponseEntity.noContent().build()
    }

    // DTO classes
    data class QuizAnswerRequest(
        val answer: String,
        val sortOrder: Int = 0
    ) {
        fun toDomain(): QuizAnswer {
            return QuizAnswer(
                answer = answer,
                sortOrder = sortOrder
            )
        }
    }

    data class QuizAnswerResponse(
        val id: Long?,
        val answer: String,
        val sortOrder: Int,
        val createdBy: String?,
        val createdAt: String?,
        val updatedBy: String?,
        val updatedAt: String?
    ) {
        companion object {
            fun fromDomain(quizAnswer: QuizAnswer): QuizAnswerResponse {
                return QuizAnswerResponse(
                    id = quizAnswer.id,
                    answer = quizAnswer.answer,
                    sortOrder = quizAnswer.sortOrder,
                    createdBy = quizAnswer.createdBy,
                    createdAt = quizAnswer.createdAt,
                    updatedBy = quizAnswer.updatedBy,
                    updatedAt = quizAnswer.updatedAt
                )
            }
        }
    }
}
