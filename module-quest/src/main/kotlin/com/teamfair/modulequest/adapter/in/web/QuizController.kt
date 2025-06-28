package com.teamfair.modulequest.adapter.`in`.web

import com.teamfair.modulequest.application.port.`in`.QuizUseCase
import com.teamfair.modulequest.domain.model.Quiz
import com.teamfair.modulequest.domain.model.QuizAnswer
import com.teamfair.modulequest.adapter.`in`.web.QuizAnswerController.QuizAnswerRequest
import com.teamfair.modulequest.adapter.`in`.web.QuizAnswerController.QuizAnswerResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/quizzes")
class QuizController(private val quizUseCase: QuizUseCase) {

    // Quiz CRUD endpoints

    @PostMapping
    fun createQuiz(@RequestBody quizRequest: QuizRequest, @RequestParam missionId: Long): ResponseEntity<QuizResponse> {
        val quiz = quizRequest.toDomain()
        val createdQuiz = quizUseCase.createQuiz(quiz, missionId)
        return ResponseEntity(QuizResponse.fromDomain(createdQuiz), HttpStatus.CREATED)
    }

    @GetMapping("/{id}")
    fun getQuiz(@PathVariable id: Long): ResponseEntity<QuizResponse> {
        val quiz = quizUseCase.getQuiz(id)
        return ResponseEntity.ok(QuizResponse.fromDomain(quiz))
    }

    @GetMapping
    fun getAllQuizzes(): ResponseEntity<List<QuizResponse>> {
        val quizzes = quizUseCase.getAllQuizzes()
        return ResponseEntity.ok(quizzes.map { QuizResponse.fromDomain(it) })
    }

    @GetMapping("/mission/{missionId}")
    fun getQuizzesByMissionId(@PathVariable missionId: Long): ResponseEntity<List<QuizResponse>> {
        val quizzes = quizUseCase.getQuizzesByMissionId(missionId)
        return ResponseEntity.ok(quizzes.map { QuizResponse.fromDomain(it) })
    }

    @PutMapping("/{id}")
    fun updateQuiz(@PathVariable id: Long, @RequestBody quizRequest: QuizRequest): ResponseEntity<QuizResponse> {
        val quiz = quizRequest.toDomain()
        val updatedQuiz = quizUseCase.updateQuiz(id, quiz)
        return ResponseEntity.ok(QuizResponse.fromDomain(updatedQuiz))
    }

    @DeleteMapping("/{id}")
    fun deleteQuiz(@PathVariable id: Long): ResponseEntity<Void> {
        quizUseCase.deleteQuiz(id)
        return ResponseEntity.noContent().build()
    }

    // DTO classes

    data class QuizRequest(
        val question: String,
        val hint: String? = null,
        val sortOrder: Int = 0,
        val answers: List<QuizAnswerRequest> = emptyList()
    ) {
        fun toDomain(): Quiz {
            return Quiz(
                question = question,
                hint = hint,
                sortOrder = sortOrder,
                answers = answers.map { it.toDomain() }.toMutableList()
            )
        }
    }

    data class QuizResponse(
        val id: Long?,
        val question: String,
        val hint: String?,
        val sortOrder: Int,
        val answers: List<QuizAnswerResponse> = emptyList(),
        val createdBy: String?,
        val createdAt: String?,
        val updatedBy: String?,
        val updatedAt: String?
    ) {
        companion object {
            fun fromDomain(quiz: Quiz): QuizResponse {
                return QuizResponse(
                    id = quiz.id,
                    question = quiz.question,
                    hint = quiz.hint,
                    sortOrder = quiz.sortOrder,
                    answers = quiz.answers.map { QuizAnswerResponse.fromDomain(it) },
                    createdBy = quiz.createdBy,
                    createdAt = quiz.createdAt,
                    updatedBy = quiz.updatedBy,
                    updatedAt = quiz.updatedAt
                )
            }
        }
    }

}
