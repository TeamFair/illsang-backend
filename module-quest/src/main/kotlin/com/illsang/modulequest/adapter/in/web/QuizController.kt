package com.illsang.modulequest.adapter.`in`.web

import com.illsang.common.enums.ResponseMsg
import com.illsang.modulequest.adapter.`in`.web.model.request.CreateQuizRequest
import com.illsang.modulequest.adapter.`in`.web.model.request.UpdateQuizRequest
import com.illsang.modulequest.adapter.`in`.web.model.response.QuizResponse
import com.illsang.modulequest.application.command.CreateQuizCommand
import com.illsang.modulequest.application.command.UpdateQuizCommand
import com.illsang.modulequest.application.service.QuizManagementService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/quizzes")
class QuizController(
    private val quizManagementService: QuizManagementService
) {

    @PostMapping
    fun createQuiz(@RequestBody request: CreateQuizRequest): ResponseEntity<QuizResponse> {
        val command = CreateQuizCommand(
            question = request.question,
            hint = request.hint,
            sortOrder = request.sortOrder,
            missionId = request.missionId
        )
        val quiz = quizManagementService.createQuiz(command)
        return ResponseEntity.status(HttpStatus.CREATED).body(QuizResponse.from(quiz))
    }

    @GetMapping("/{id}")
    fun getQuiz(@PathVariable id: Long): ResponseEntity<QuizResponse> {
        val quiz = quizManagementService.getQuizById(id)
        return if (quiz != null) {
            ResponseEntity.ok(QuizResponse.from(quiz))
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @GetMapping
    fun getAllQuizzes(): ResponseEntity<List<QuizResponse>> {
        val quizzes = quizManagementService.getAllQuizzes()
        return ResponseEntity.ok(quizzes.map { QuizResponse.from(it) })
    }

    @GetMapping("/mission/{missionId}")
    fun getQuizzesByMissionId(@PathVariable missionId: Long): ResponseEntity<List<QuizResponse>> {
        val quizzes = quizManagementService.getQuizzesByMissionId(missionId)
        return ResponseEntity.ok(quizzes.map { QuizResponse.from(it) })
    }

    @PutMapping("/{id}")
    fun updateQuiz(
        @PathVariable id: Long,
        @RequestBody request: UpdateQuizRequest
    ): ResponseEntity<QuizResponse> {
        val command = UpdateQuizCommand(
            id = id,
            question = request.question,
            hint = request.hint,
            sortOrder = request.sortOrder
        )
        val quiz = quizManagementService.updateQuiz(command)
        return if (quiz != null) {
            ResponseEntity.ok(QuizResponse.from(quiz))
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/{id}")
    fun deleteQuiz(@PathVariable id: Long): ResponseEntity<ResponseMsg> {
        val deleted = quizManagementService.deleteQuiz(id)
        return if (deleted) {
            ResponseEntity.ok(ResponseMsg.SUCCESS)
        } else {
            ResponseEntity.notFound().build()
        }
    }
}
