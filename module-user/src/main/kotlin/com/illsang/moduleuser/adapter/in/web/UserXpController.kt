package com.illsang.moduleuser.adapter.`in`.web

import com.illsang.common.enums.ResponseMsg
import com.illsang.moduleuser.adapter.`in`.web.model.request.AddPointRequest
import com.illsang.moduleuser.adapter.`in`.web.model.request.CreateUserXpRequest
import com.illsang.moduleuser.adapter.`in`.web.model.request.UpdateUserXpRequest
import com.illsang.moduleuser.adapter.`in`.web.model.response.UserXpResponse
import com.illsang.moduleuser.application.command.CreateUserXpCommand
import com.illsang.moduleuser.application.command.UpdateUserXpCommand
import com.illsang.moduleuser.application.service.UserXpService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/user-xps")
class UserXpController(
    private val userXpService: UserXpService
) {

    /**
     * create user xp
     */
    @PostMapping
    fun createUserXp(@RequestBody request: CreateUserXpRequest): ResponseEntity<UserXpResponse> {
        val command = CreateUserXpCommand(
            userId = request.userId,
            xpType = request.xpType,
            point = request.point
        )
        val userXp = userXpService.createUserXp(command)
        return ResponseEntity.status(HttpStatus.CREATED).body(UserXpResponse.from(userXp))
    }

    /**
     * find user xp by id
     */
    @GetMapping("/{id}")
    fun getUserXp(@PathVariable id: Long): ResponseEntity<UserXpResponse> {
        val userXp = userXpService.getUserXpById(id)
        return if (userXp != null) {
            ResponseEntity.ok(UserXpResponse.from(userXp))
        } else {
            ResponseEntity.notFound().build()
        }
    }

    /**
     * find all user xp
     */
    @GetMapping("/user/{userId}")
    fun getUserXpsByUserId(@PathVariable userId: Long): ResponseEntity<List<UserXpResponse>> {
        val userXps = userXpService.getUserXpsByUserId(userId)
        return ResponseEntity.ok(userXps.map { UserXpResponse.from(it) })
    }

    /**
     * find user xp by user id and xp type
     */
    @GetMapping("/user/{userId}/type/{xpType}")
    fun getUserXpByUserIdAndXpType(
        @PathVariable userId: Long,
        @PathVariable xpType: com.illsang.moduleuser.domain.model.XpType
    ): ResponseEntity<UserXpResponse> {
        val userXp = userXpService.getUserXpByUserIdAndXpType(userId, xpType)
        return if (userXp != null) {
            ResponseEntity.ok(UserXpResponse.from(userXp))
        } else {
            ResponseEntity.notFound().build()
        }
    }

    /**
     * update user xp
     */
    @PutMapping("/{id}")
    fun updateUserXp(
        @PathVariable id: Long,
        @RequestBody request: UpdateUserXpRequest
    ): ResponseEntity<UserXpResponse> {
        val command = UpdateUserXpCommand(
            id = id,
            xpType = request.xpType,
            point = request.point
        )
        val userXp = userXpService.updateUserXp(command)
        return if (userXp != null) {
            ResponseEntity.ok(UserXpResponse.from(userXp))
        } else {
            ResponseEntity.notFound().build()
        }
    }

    /**
     * add point to user xp
     */
    @PostMapping("/user/{userId}/add-point")
    fun addPoint(
        @PathVariable userId: Long,
        @RequestBody request: AddPointRequest
    ): ResponseEntity<UserXpResponse> {
        val userXp = userXpService.addPoint(userId, request.xpType, request.additionalPoint)
        return if (userXp != null) {
            ResponseEntity.ok(UserXpResponse.from(userXp))
        } else {
            ResponseEntity.notFound().build()
        }
    }

    /**
     * delete user xp
     *
     * @param id user xp id
     * @return response entity
     */
    @DeleteMapping("/{id}")
    fun deleteUserXp(@PathVariable id: Long): ResponseEntity<ResponseMsg> {
        val deleted = userXpService.deleteUserXp(id)
        return if (deleted) {
            ResponseEntity.ok(ResponseMsg.SUCCESS)
        } else {
            ResponseEntity.notFound().build()
        }
    }
}
