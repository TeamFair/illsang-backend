package com.teamfair.modulequest.adapter.`in`.web

import com.illsang.common.enums.ResponseMsg
import com.teamfair.modulequest.adapter.`in`.web.model.request.CreateUserQuestHistoryRequest
import com.teamfair.modulequest.adapter.`in`.web.model.request.UpdateUserQuestHistoryRequest
import com.teamfair.modulequest.adapter.`in`.web.model.response.UserQuestHistoryResponse
import com.teamfair.modulequest.application.command.CreateUserQuestHistoryCommand
import com.teamfair.modulequest.application.command.UpdateUserQuestHistoryCommand
import com.teamfair.modulequest.application.service.UserQuestHistoryService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/user-quest-histories")
class UserQuestHistoryController(
    private val userQuestHistoryService: UserQuestHistoryService
) {

    @PostMapping
    fun createUserQuestHistory(@RequestBody request: CreateUserQuestHistoryRequest): ResponseEntity<Any> {
        val command = CreateUserQuestHistoryCommand(
            userId = request.userId,
            status = request.status,
            liked = request.liked,
            disliked = request.disliked,
            viewCount = request.viewCount,
            questId = request.questId
        )
        
        val userQuestHistory = userQuestHistoryService.createUserQuestHistory(command)
        val response = UserQuestHistoryResponse(
            id = userQuestHistory.id!!,
            userId = userQuestHistory.userId,
            status = userQuestHistory.status,
            liked = userQuestHistory.liked,
            disliked = userQuestHistory.disliked,
            viewCount = userQuestHistory.viewCount,
            questId = userQuestHistory.questId,
            createdBy = userQuestHistory.createdBy,
            createdAt = userQuestHistory.createdAt,
            updatedBy = userQuestHistory.updatedBy,
            updatedAt = userQuestHistory.updatedAt
        )
        
        return ResponseEntity.ok(response)
    }

    @GetMapping("/{id}")
    fun getUserQuestHistoryById(@PathVariable id: Long): ResponseEntity<Any> {
        val userQuestHistory = userQuestHistoryService.getUserQuestHistoryById(id)
        return if (userQuestHistory != null) {
            val response = UserQuestHistoryResponse(
                id = userQuestHistory.id!!,
                userId = userQuestHistory.userId,
                status = userQuestHistory.status,
                liked = userQuestHistory.liked,
                disliked = userQuestHistory.disliked,
                viewCount = userQuestHistory.viewCount,
                questId = userQuestHistory.questId,
                createdBy = userQuestHistory.createdBy,
                createdAt = userQuestHistory.createdAt,
                updatedBy = userQuestHistory.updatedBy,
                updatedAt = userQuestHistory.updatedAt
            )
            ResponseEntity.ok(response)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @GetMapping
    fun getAllUserQuestHistories(): ResponseEntity<List<UserQuestHistoryResponse>> {
        val userQuestHistories = userQuestHistoryService.getAllUserQuestHistories()
        val responses = userQuestHistories.map { userQuestHistory ->
            UserQuestHistoryResponse(
                id = userQuestHistory.id!!,
                userId = userQuestHistory.userId,
                status = userQuestHistory.status,
                liked = userQuestHistory.liked,
                disliked = userQuestHistory.disliked,
                viewCount = userQuestHistory.viewCount,
                questId = userQuestHistory.questId,
                createdBy = userQuestHistory.createdBy,
                createdAt = userQuestHistory.createdAt,
                updatedBy = userQuestHistory.updatedBy,
                updatedAt = userQuestHistory.updatedAt
            )
        }
        return ResponseEntity.ok(responses)
    }

    @GetMapping("/user/{userId}")
    fun getUserQuestHistoriesByUserId(@PathVariable userId: Long): ResponseEntity<List<UserQuestHistoryResponse>> {
        val userQuestHistories = userQuestHistoryService.getUserQuestHistoriesByUserId(userId)
        val responses = userQuestHistories.map { userQuestHistory ->
            UserQuestHistoryResponse(
                id = userQuestHistory.id!!,
                userId = userQuestHistory.userId,
                status = userQuestHistory.status,
                liked = userQuestHistory.liked,
                disliked = userQuestHistory.disliked,
                viewCount = userQuestHistory.viewCount,
                questId = userQuestHistory.questId,
                createdBy = userQuestHistory.createdBy,
                createdAt = userQuestHistory.createdAt,
                updatedBy = userQuestHistory.updatedBy,
                updatedAt = userQuestHistory.updatedAt
            )
        }
        return ResponseEntity.ok(responses)
    }

    @GetMapping("/quest/{questId}")
    fun getUserQuestHistoriesByQuestId(@PathVariable questId: Long): ResponseEntity<List<UserQuestHistoryResponse>> {
        val userQuestHistories = userQuestHistoryService.getUserQuestHistoriesByQuestId(questId)
        val responses = userQuestHistories.map { userQuestHistory ->
            UserQuestHistoryResponse(
                id = userQuestHistory.id!!,
                userId = userQuestHistory.userId,
                status = userQuestHistory.status,
                liked = userQuestHistory.liked,
                disliked = userQuestHistory.disliked,
                viewCount = userQuestHistory.viewCount,
                questId = userQuestHistory.questId,
                createdBy = userQuestHistory.createdBy,
                createdAt = userQuestHistory.createdAt,
                updatedBy = userQuestHistory.updatedBy,
                updatedAt = userQuestHistory.updatedAt
            )
        }
        return ResponseEntity.ok(responses)
    }

    @PutMapping("/{id}")
    fun updateUserQuestHistory(
        @PathVariable id: Long,
        @RequestBody request: UpdateUserQuestHistoryRequest
    ): ResponseEntity<Any> {
        val command = UpdateUserQuestHistoryCommand(
            id = id,
            status = request.status,
            liked = request.liked,
            disliked = request.disliked,
            viewCount = request.viewCount
        )
        
        val userQuestHistory = userQuestHistoryService.updateUserQuestHistory(command)
        return if (userQuestHistory != null) {
            val response = UserQuestHistoryResponse(
                id = userQuestHistory.id!!,
                userId = userQuestHistory.userId,
                status = userQuestHistory.status,
                liked = userQuestHistory.liked,
                disliked = userQuestHistory.disliked,
                viewCount = userQuestHistory.viewCount,
                questId = userQuestHistory.questId,
                createdBy = userQuestHistory.createdBy,
                createdAt = userQuestHistory.createdAt,
                updatedBy = userQuestHistory.updatedBy,
                updatedAt = userQuestHistory.updatedAt
            )
            ResponseEntity.ok(response)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/{id}")
    fun deleteUserQuestHistory(@PathVariable id: Long): ResponseEntity<ResponseMsg> {
        val deleted = userQuestHistoryService.deleteUserQuestHistory(id)
        return if (deleted) {
            ResponseEntity.ok(ResponseMsg.SUCCESS)
        } else {
            ResponseEntity.notFound().build()
        }
    }
} 