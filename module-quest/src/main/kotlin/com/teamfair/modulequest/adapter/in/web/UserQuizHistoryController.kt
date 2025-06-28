package com.teamfair.modulequest.adapter.`in`.web

import com.illsang.common.enums.ResponseMsg
import com.teamfair.modulequest.adapter.`in`.web.model.request.CreateUserQuizHistoryRequest
import com.teamfair.modulequest.adapter.`in`.web.model.request.UpdateUserQuizHistoryRequest
import com.teamfair.modulequest.adapter.`in`.web.model.response.UserQuizHistoryResponse
import com.teamfair.modulequest.application.command.CreateUserQuizHistoryCommand
import com.teamfair.modulequest.application.command.UpdateUserQuizHistoryCommand
import com.teamfair.modulequest.application.service.UserQuizHistoryService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/user-quiz-histories")
class UserQuizHistoryController(
    private val userQuizHistoryService: UserQuizHistoryService
) {

    @PostMapping
    fun createUserQuizHistory(@RequestBody request: CreateUserQuizHistoryRequest): ResponseEntity<Any> {
        val command = CreateUserQuizHistoryCommand(
            userId = request.userId,
            answer = request.answer,
            submittedAt = request.submittedAt,
            quizId = request.quizId,
            userMissionHistoryId = request.userMissionHistoryId
        )
        val userQuizHistory = userQuizHistoryService.createUserQuizHistory(command)
        val response = UserQuizHistoryResponse(
            id = userQuizHistory.id!!,
            userId = userQuizHistory.userId,
            answer = userQuizHistory.answer,
            submittedAt = userQuizHistory.submittedAt,
            quizId = userQuizHistory.quizId,
            userMissionHistoryId = userQuizHistory.userMissionHistoryId,
            createdBy = userQuizHistory.createdBy,
            createdAt = userQuizHistory.createdAt,
            updatedBy = userQuizHistory.updatedBy,
            updatedAt = userQuizHistory.updatedAt
        )
        return ResponseEntity.ok(response)
    }

    @GetMapping("/{id}")
    fun getUserQuizHistoryById(@PathVariable id: Long): ResponseEntity<Any> {
        val userQuizHistory = userQuizHistoryService.getUserQuizHistoryById(id)
        return if (userQuizHistory != null) {
            val response = UserQuizHistoryResponse(
                id = userQuizHistory.id!!,
                userId = userQuizHistory.userId,
                answer = userQuizHistory.answer,
                submittedAt = userQuizHistory.submittedAt,
                quizId = userQuizHistory.quizId,
                userMissionHistoryId = userQuizHistory.userMissionHistoryId,
                createdBy = userQuizHistory.createdBy,
                createdAt = userQuizHistory.createdAt,
                updatedBy = userQuizHistory.updatedBy,
                updatedAt = userQuizHistory.updatedAt
            )
            ResponseEntity.ok(response)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @GetMapping
    fun getAllUserQuizHistories(): ResponseEntity<List<UserQuizHistoryResponse>> {
        val userQuizHistories = userQuizHistoryService.getAllUserQuizHistories()
        val responses = userQuizHistories.map { userQuizHistory ->
            UserQuizHistoryResponse(
                id = userQuizHistory.id!!,
                userId = userQuizHistory.userId,
                answer = userQuizHistory.answer,
                submittedAt = userQuizHistory.submittedAt,
                quizId = userQuizHistory.quizId,
                userMissionHistoryId = userQuizHistory.userMissionHistoryId,
                createdBy = userQuizHistory.createdBy,
                createdAt = userQuizHistory.createdAt,
                updatedBy = userQuizHistory.updatedBy,
                updatedAt = userQuizHistory.updatedAt
            )
        }
        return ResponseEntity.ok(responses)
    }

    @GetMapping("/user/{userId}")
    fun getUserQuizHistoriesByUserId(@PathVariable userId: Long): ResponseEntity<List<UserQuizHistoryResponse>> {
        val userQuizHistories = userQuizHistoryService.getUserQuizHistoriesByUserId(userId)
        val responses = userQuizHistories.map { userQuizHistory ->
            UserQuizHistoryResponse(
                id = userQuizHistory.id!!,
                userId = userQuizHistory.userId,
                answer = userQuizHistory.answer,
                submittedAt = userQuizHistory.submittedAt,
                quizId = userQuizHistory.quizId,
                userMissionHistoryId = userQuizHistory.userMissionHistoryId,
                createdBy = userQuizHistory.createdBy,
                createdAt = userQuizHistory.createdAt,
                updatedBy = userQuizHistory.updatedBy,
                updatedAt = userQuizHistory.updatedAt
            )
        }
        return ResponseEntity.ok(responses)
    }

    @GetMapping("/quiz/{quizId}")
    fun getUserQuizHistoriesByQuizId(@PathVariable quizId: Long): ResponseEntity<List<UserQuizHistoryResponse>> {
        val userQuizHistories = userQuizHistoryService.getUserQuizHistoriesByQuizId(quizId)
        val responses = userQuizHistories.map { userQuizHistory ->
            UserQuizHistoryResponse(
                id = userQuizHistory.id!!,
                userId = userQuizHistory.userId,
                answer = userQuizHistory.answer,
                submittedAt = userQuizHistory.submittedAt,
                quizId = userQuizHistory.quizId,
                userMissionHistoryId = userQuizHistory.userMissionHistoryId,
                createdBy = userQuizHistory.createdBy,
                createdAt = userQuizHistory.createdAt,
                updatedBy = userQuizHistory.updatedBy,
                updatedAt = userQuizHistory.updatedAt
            )
        }
        return ResponseEntity.ok(responses)
    }

    @GetMapping("/user-mission-history/{userMissionHistoryId}")
    fun getUserQuizHistoriesByUserMissionHistoryId(@PathVariable userMissionHistoryId: Long): ResponseEntity<List<UserQuizHistoryResponse>> {
        val userQuizHistories = userQuizHistoryService.getUserQuizHistoriesByUserMissionHistoryId(userMissionHistoryId)
        val responses = userQuizHistories.map { userQuizHistory ->
            UserQuizHistoryResponse(
                id = userQuizHistory.id!!,
                userId = userQuizHistory.userId,
                answer = userQuizHistory.answer,
                submittedAt = userQuizHistory.submittedAt,
                quizId = userQuizHistory.quizId,
                userMissionHistoryId = userQuizHistory.userMissionHistoryId,
                createdBy = userQuizHistory.createdBy,
                createdAt = userQuizHistory.createdAt,
                updatedBy = userQuizHistory.updatedBy,
                updatedAt = userQuizHistory.updatedAt
            )
        }
        return ResponseEntity.ok(responses)
    }

    @PutMapping("/{id}")
    fun updateUserQuizHistory(
        @PathVariable id: Long,
        @RequestBody request: UpdateUserQuizHistoryRequest
    ): ResponseEntity<Any> {
        val command = UpdateUserQuizHistoryCommand(
            id = id,
            answer = request.answer,
            submittedAt = request.submittedAt
        )
        val userQuizHistory = userQuizHistoryService.updateUserQuizHistory(command)
        return if (userQuizHistory != null) {
            val response = UserQuizHistoryResponse(
                id = userQuizHistory.id!!,
                userId = userQuizHistory.userId,
                answer = userQuizHistory.answer,
                submittedAt = userQuizHistory.submittedAt,
                quizId = userQuizHistory.quizId,
                userMissionHistoryId = userQuizHistory.userMissionHistoryId,
                createdBy = userQuizHistory.createdBy,
                createdAt = userQuizHistory.createdAt,
                updatedBy = userQuizHistory.updatedBy,
                updatedAt = userQuizHistory.updatedAt
            )
            ResponseEntity.ok(response)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/{id}")
    fun deleteUserQuizHistory(@PathVariable id: Long): ResponseEntity<ResponseMsg> {
        val deleted = userQuizHistoryService.deleteUserQuizHistory(id)
        return if (deleted) {
            ResponseEntity.ok(ResponseMsg.SUCCESS)
        } else {
            ResponseEntity.notFound().build()
        }
    }
} 