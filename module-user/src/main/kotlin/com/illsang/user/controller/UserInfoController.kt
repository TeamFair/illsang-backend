package com.illsang.user.controller

import com.illsang.auth.model.AuthenticationModel
import com.illsang.user.dto.request.UpdateUserNickNameRequest
import com.illsang.user.dto.request.UpdateUserProfileImageRequest
import com.illsang.user.dto.response.UserInfoResponse
import com.illsang.user.service.UserService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/user")
class UserInfoController(
    private val userService: UserService,
) {
    @GetMapping
    fun getUser(
        @RequestParam id: String?,
        @AuthenticationPrincipal auth: AuthenticationModel,
    ): ResponseEntity<UserInfoResponse> {
        val user = if (id.isNullOrBlank()) {
            userService.getUser(auth.userId)
        } else {
            userService.getUser(id)
        }

        return ResponseEntity.ok(UserInfoResponse.from(user))
    }

    @PutMapping("/profile/nickname")
    fun updateNickname(
        @AuthenticationPrincipal auth: AuthenticationModel,
        @RequestBody request: UpdateUserNickNameRequest,
    ): ResponseEntity<UserInfoResponse> {
        val user = userService.updateNickname(auth.userId, request)

        return ResponseEntity.ok(UserInfoResponse.from(user))
    }

    @PutMapping("/profile/image")
    fun updateProfileImage(
        @AuthenticationPrincipal auth: AuthenticationModel,
        @Valid @RequestBody request: UpdateUserProfileImageRequest,
    ): ResponseEntity<UserInfoResponse> {
        val user = userService.updateProfileImage(auth.userId, request)

        return ResponseEntity.ok(UserInfoResponse.from(user))
    }

    @PutMapping("/profile/title")
    fun updateTitle(
        @AuthenticationPrincipal auth: AuthenticationModel,
        @RequestParam(required = false) titleHistoryId: String?,
    ) : ResponseEntity<UserInfoResponse> {
        val user = userService.updateTitle(auth.userId, titleHistoryId)

        return ResponseEntity.ok(UserInfoResponse.from(user))
    }
}
