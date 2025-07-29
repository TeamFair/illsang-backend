package com.illsang.user.adapter.`in`.web

import com.illsang.common.enums.ResponseMsg
import com.illsang.user.dto.request.CreateUserXpHistoryRequest
import com.illsang.user.dto.request.UpdateUserXpHistoryRequest
import com.illsang.user.dto.response.UserXpHistoryResponse
import com.illsang.user.application.command.CreateUserXpHistoryCommand
import com.illsang.user.application.command.UpdateUserXpHistoryCommand
import com.illsang.user.service.UserXpHistoryService
import com.illsang.user.enums.XpType
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/user-xp-histories")
class UserXpHistoryController(
    private val userXpHistoryService: UserXpHistoryService
) {

    @PostMapping
    fun createUserXpHistory(@RequestBody request: CreateUserXpHistoryRequest): ResponseEntity<UserXpHistoryResponse> {
        val command = CreateUserXpHistoryCommand(
            userId = request.userId,
            xpType = request.xpType,
            point = request.point
        )
        val userXpHistory = userXpHistoryService.createUserXpHistory(command)
        return ResponseEntity.status(HttpStatus.CREATED).body(UserXpHistoryResponse.from(userXpHistory))
    }

    @GetMapping("/{id}")
    fun getUserXpHistory(@PathVariable id: Long): ResponseEntity<UserXpHistoryResponse> {
        val userXpHistory = userXpHistoryService.getUserXpHistoryById(id)
        return if (userXpHistory != null) {
            ResponseEntity.ok(UserXpHistoryResponse.from(userXpHistory))
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @GetMapping("/user/{userId}")
    fun getUserXpHistoriesByUserId(@PathVariable userId: Long): ResponseEntity<List<UserXpHistoryResponse>> {
        val userXpHistories = userXpHistoryService.getUserXpHistoriesByUserId(userId)
        return ResponseEntity.ok(userXpHistories.map { UserXpHistoryResponse.from(it) })
    }

    @GetMapping("/user/{userId}/type/{xpType}")
    fun getUserXpHistoriesByUserIdAndXpType(
        @PathVariable userId: Long,
        @PathVariable xpType: XpType
    ): ResponseEntity<List<UserXpHistoryResponse>> {
        val userXpHistories = userXpHistoryService.getUserXpHistoriesByUserIdAndXpType(userId, xpType)
        return ResponseEntity.ok(userXpHistories.map { UserXpHistoryResponse.from(it) })
    }

    @PutMapping("/{id}")
    fun updateUserXpHistory(
        @PathVariable id: Long,
        @RequestBody request: UpdateUserXpHistoryRequest
    ): ResponseEntity<UserXpHistoryResponse> {
        val command = UpdateUserXpHistoryCommand(
            id = id,
            xpType = request.xpType,
            point = request.point
        )
        val userXpHistory = userXpHistoryService.updateUserXpHistory(command)
        return if (userXpHistory != null) {
            ResponseEntity.ok(UserXpHistoryResponse.from(userXpHistory))
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/{id}")
    fun deleteUserXpHistory(@PathVariable id: Long): ResponseEntity<ResponseMsg> {
        val deleted = userXpHistoryService.deleteUserXpHistory(id)
        return if (deleted) {
            ResponseEntity.ok(ResponseMsg.SUCCESS)
        } else {
            ResponseEntity.notFound().build()
        }
    }
}
