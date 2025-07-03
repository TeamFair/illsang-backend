package com.illsang.moduleuser.adapter.`in`.web

import com.illsang.common.enums.ResponseMsg
import com.illsang.moduleuser.application.command.CreateUserCommand
import com.illsang.moduleuser.application.command.UpdateUserCommand
import com.illsang.moduleuser.application.service.UserService
import com.illsang.moduleuser.domain.model.UserModel
import jakarta.annotation.security.PermitAll
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/users")
class UserController(
    private val userService: UserService
) {

    /**
     * create user
     */
    @PostMapping
    fun createUser(@RequestBody command: CreateUserCommand): ResponseEntity<UserModel> {
        val createdUser = userService.createUser(command)
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser)
    }

    /**
     * find user by id
     */
    @GetMapping("/{id}")
    fun getUser(@PathVariable id: Long): ResponseEntity<UserModel> {
        val user = userService.getUser(id)
        return ResponseEntity.ok(user)
    }

    /**
     * find user by email
     */
    @GetMapping("/email/{email}")
    fun getUserByEmail(@PathVariable email: String): ResponseEntity<UserModel> {
        val user = userService.getUserByEmail(email)
        return ResponseEntity.ok(user)
    }

    /**
     * find all users
     */
    @GetMapping
    fun getAllUsers(): ResponseEntity<List<UserModel>> {
        val users = userService.getAllUsers()
        return ResponseEntity.ok(users)
    }

    /**
     * update user
     */
    @PutMapping("/{id}")
    fun updateUser(
        @PathVariable id: Long,
        @RequestBody command: UpdateUserCommand
    ): ResponseEntity<UserModel> {
        val updatedCommand = command.copy(id = id)
        val updatedUser = userService.updateUser(updatedCommand)
        return ResponseEntity.ok(updatedUser)
    }

    /**
     * delete user
     */
    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id: Long): ResponseEntity<ResponseMsg> {
        userService.deleteUser(id)
        return ResponseEntity.ok(ResponseMsg.SUCCESS)
    }
}
