package com.teamfair.modulequest.adapter.`in`.web

import com.illsang.common.enums.ResponseMsg
import com.teamfair.modulequest.adapter.`in`.web.model.request.CreateUserQuizHistoryRequest
import com.teamfair.modulequest.adapter.`in`.web.model.request.UpdateUserQuizHistoryRequest
import com.teamfair.modulequest.application.command.CreateUserQuizHistoryCommand
import com.teamfair.modulequest.application.command.UpdateUserQuizHistoryCommand
import com.teamfair.modulequest.application.service.UserQuizHistoryService
import com.teamfair.modulequest.domain.model.UserQuizHistoryModel
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/user-quiz-histories")
class UserQuizHistoryController(
    private val userQuizHistoryService: UserQuizHistoryService
) {

    @PostMapping
    fun createUserQuizHistory(@RequestBody request: CreateUserQuizHistoryRequest): ResponseEntity<UserQuizHistoryModel> {
        val command = CreateUserQuizHistoryCommand(
            userId = request.userId,
            answer = request.answer,
            submittedAt = request.submittedAt,
            quizId = request.quizId,
            userMissionHistoryId = request.userMissionHistoryId
        )
        val userQuizHistory = userQuizHistoryService.createUserQuizHistory(command)
        return ResponseEntity.ok(userQuizHistory)
    }

    @GetMapping("/{id}")
    fun getUserQuizHistoryById(@PathVariable id: Long): ResponseEntity<Any> {
        val userQuizHistory = userQuizHistoryService.getUserQuizHistoryById(id)
        return if (userQuizHistory != null) {
            ResponseEntity.ok(userQuizHistory)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @GetMapping
    fun getAllUserQuizHistories(): ResponseEntity<List<UserQuizHistoryModel>> {
        val userQuizHistories = userQuizHistoryService.getAllUserQuizHistories()
        return ResponseEntity.ok(userQuizHistories)
    }

    @GetMapping("/user/{userId}")
    fun getUserQuizHistoriesByUserId(@PathVariable userId: Long): ResponseEntity<List<UserQuizHistoryModel>> {
        val userQuizHistories = userQuizHistoryService.getUserQuizHistoriesByUserId(userId)
        return ResponseEntity.ok(userQuizHistories)
    }

    @GetMapping("/quiz/{quizId}")
    fun getUserQuizHistoriesByQuizId(@PathVariable quizId: Long): ResponseEntity<List<UserQuizHistoryModel>> {
        val userQuizHistories = userQuizHistoryService.getUserQuizHistoriesByQuizId(quizId)
        return ResponseEntity.ok(userQuizHistories)
    }

    @GetMapping("/user-mission-history/{userMissionHistoryId}")
    fun getUserQuizHistoriesByUserMissionHistoryId(@PathVariable userMissionHistoryId: Long): ResponseEntity<List<UserQuizHistoryModel>> {
        val userQuizHistories = userQuizHistoryService.getUserQuizHistoriesByUserMissionHistoryId(userMissionHistoryId)
        return ResponseEntity.ok(userQuizHistories)
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
            ResponseEntity.ok(userQuizHistory)
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