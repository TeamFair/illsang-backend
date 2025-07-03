package com.illsang.modulequest.adapter.`in`.web

import com.illsang.common.enums.ResponseMsg
import com.illsang.modulequest.adapter.`in`.web.model.request.CreateUserMissionHistoryRequest
import com.illsang.modulequest.adapter.`in`.web.model.request.UpdateUserMissionHistoryRequest
import com.illsang.modulequest.adapter.`in`.web.model.response.UserMissionHistoryResponse
import com.illsang.modulequest.application.command.CreateUserMissionHistoryCommand
import com.illsang.modulequest.application.command.UpdateUserMissionHistoryCommand
import com.illsang.modulequest.application.service.UserMissionHistoryService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/user-mission-histories")
class UserMissionHistoryController(
    private val userMissionHistoryService: UserMissionHistoryService
) {

    @PostMapping
    fun createUserMissionHistory(@RequestBody request: CreateUserMissionHistoryRequest): ResponseEntity<UserMissionHistoryResponse> {
        val command = CreateUserMissionHistoryCommand(
            userId = request.userId,
            status = request.status,
            submissionImageUrl = request.submissionImageUrl,
            submittedAt = request.submittedAt,
            missionId = request.missionId,
            userQuestHistoryId = request.userQuestHistoryId
        )
        val userMissionHistory = userMissionHistoryService.createUserMissionHistory(command)
        return ResponseEntity.status(HttpStatus.CREATED).body(UserMissionHistoryResponse.from(userMissionHistory))
    }

    @GetMapping("/{id}")
    fun getUserMissionHistory(@PathVariable id: Long): ResponseEntity<UserMissionHistoryResponse> {
        val userMissionHistory = userMissionHistoryService.getUserMissionHistoryById(id)
        return if (userMissionHistory != null) {
            ResponseEntity.ok(UserMissionHistoryResponse.from(userMissionHistory))
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @GetMapping
    fun getAllUserMissionHistories(): ResponseEntity<List<UserMissionHistoryResponse>> {
        val userMissionHistories = userMissionHistoryService.getAllUserMissionHistories()
        return ResponseEntity.ok(userMissionHistories.map { UserMissionHistoryResponse.from(it) })
    }

    @GetMapping("/user/{userId}")
    fun getUserMissionHistoriesByUserId(@PathVariable userId: Long): ResponseEntity<List<UserMissionHistoryResponse>> {
        val userMissionHistories = userMissionHistoryService.getUserMissionHistoriesByUserId(userId)
        return ResponseEntity.ok(userMissionHistories.map { UserMissionHistoryResponse.from(it) })
    }

    @GetMapping("/mission/{missionId}")
    fun getUserMissionHistoriesByMissionId(@PathVariable missionId: Long): ResponseEntity<List<UserMissionHistoryResponse>> {
        val userMissionHistories = userMissionHistoryService.getUserMissionHistoriesByMissionId(missionId)
        return ResponseEntity.ok(userMissionHistories.map { UserMissionHistoryResponse.from(it) })
    }

    @PutMapping("/{id}")
    fun updateUserMissionHistory(
        @PathVariable id: Long,
        @RequestBody request: UpdateUserMissionHistoryRequest
    ): ResponseEntity<UserMissionHistoryResponse> {
        val command = UpdateUserMissionHistoryCommand(
            id = id,
            status = request.status,
            submissionImageUrl = request.submissionImageUrl,
            submittedAt = request.submittedAt
        )
        val userMissionHistory = userMissionHistoryService.updateUserMissionHistory(command)
        return if (userMissionHistory != null) {
            ResponseEntity.ok(UserMissionHistoryResponse.from(userMissionHistory))
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/{id}")
    fun deleteUserMissionHistory(@PathVariable id: Long): ResponseEntity<ResponseMsg> {
        val deleted = userMissionHistoryService.deleteUserMissionHistory(id)
        return if (deleted) {
            ResponseEntity.ok(ResponseMsg.SUCCESS)
        } else {
            ResponseEntity.notFound().build()
        }
    }
}
