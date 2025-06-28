package com.teamfair.moduleuser.adapter.`in`.web

import com.illsang.common.enums.ResponseMsg
import com.teamfair.moduleuser.application.command.CreateUserEmojiCommand
import com.teamfair.moduleuser.application.command.UpdateUserEmojiCommand
import com.teamfair.moduleuser.application.service.UserEmojiService
import com.teamfair.moduleuser.domain.model.UserEmojiModel
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/user-emojis")
class UserEmojiController(
    private val userEmojiService: UserEmojiService
) {

    @PostMapping
    fun createUserEmoji(@RequestBody command: CreateUserEmojiCommand): ResponseEntity<UserEmojiModel> {
        val userEmoji = userEmojiService.createUserEmoji(command)
        return ResponseEntity.status(HttpStatus.CREATED).body(userEmoji)
    }

    @GetMapping("/{id}")
    fun getUserEmoji(@PathVariable id: Long): ResponseEntity<UserEmojiModel> {
        val userEmoji = userEmojiService.getUserEmojiById(id)
        return if (userEmoji != null) {
            ResponseEntity.ok(userEmoji)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @GetMapping("/user/{userId}")
    fun getUserEmojisByUserId(@PathVariable userId: Long): ResponseEntity<List<UserEmojiModel>> {
        val userEmojis = userEmojiService.getUserEmojisByUserId(userId)
        return ResponseEntity.ok(userEmojis)
    }

    @PutMapping("/{id}")
    fun updateUserEmoji(
        @PathVariable id: Long,
        @RequestBody command: UpdateUserEmojiCommand
    ): ResponseEntity<UserEmojiModel> {
        val updatedCommand = command.copy(id = id)
        val userEmoji = userEmojiService.updateUserEmoji(updatedCommand)
        return if (userEmoji != null) {
            ResponseEntity.ok(userEmoji)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/{id}")
    fun deleteUserEmoji(@PathVariable id: Long): ResponseEntity<ResponseMsg> {
        val deleted = userEmojiService.deleteUserEmoji(id)
        return if (deleted) {
            ResponseEntity.ok(ResponseMsg.SUCCESS)
        } else {
            ResponseEntity.notFound().build()
        }
    }
} 