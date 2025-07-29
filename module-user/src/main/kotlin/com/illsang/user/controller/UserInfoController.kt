package com.illsang.user.controller

import com.illsang.auth.domain.model.AuthenticationModel
import com.illsang.user.dto.request.UpdateUserAreaZoneRequest
import com.illsang.user.dto.request.UpdateUserNickNameRequest
import com.illsang.user.dto.request.UpdateUserProfileImageRequest
import com.illsang.user.dto.request.UpdateUserTitleRequest
import com.illsang.user.dto.response.UserInfoResponse
import com.illsang.user.service.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/user")
@Tag(name = "User Account", description = "사용자 계정 정보")
class UserInfoController(
    private val userService: UserService,
) {
    @GetMapping
    @PreAuthorize("hasRole('USER')")
    @Operation(operationId = "USR001", summary = "사용자 정보 단일 조회")
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
    @PreAuthorize("hasRole('USER')")
    @Operation(operationId = "USR002", summary = "닉네임 변경")
    fun updateNickname(
        @AuthenticationPrincipal auth: AuthenticationModel,
        @RequestBody request: UpdateUserNickNameRequest,
    ): ResponseEntity<UserInfoResponse> {
        val user = userService.updateNickname(auth.userId, request)

        return ResponseEntity.ok(UserInfoResponse.from(user))
    }

    @PutMapping("/profile/image")
    @PreAuthorize("hasRole('USER')")
    @Operation(operationId = "USR003", summary = "프로필 이미지 변경")
    fun updateProfileImage(
        @AuthenticationPrincipal auth: AuthenticationModel,
        @Valid @RequestBody request: UpdateUserProfileImageRequest,
    ): ResponseEntity<UserInfoResponse> {
        val user = userService.updateProfileImage(auth.userId, request)

        return ResponseEntity.ok(UserInfoResponse.from(user))
    }

    @PutMapping("/profile/title")
    @PreAuthorize("hasRole('USER')")
    @Operation(operationId = "USR004", summary = "칭호 변경")
    fun updateTitle(
        @AuthenticationPrincipal auth: AuthenticationModel,
        @RequestBody request: UpdateUserTitleRequest,
    ): ResponseEntity<UserInfoResponse> {
        val user = userService.updateTitle(auth.userId, request.titleHistoryId)

        return ResponseEntity.ok(UserInfoResponse.from(user))
    }

    @PutMapping("/profile/area-zone")
    @PreAuthorize("hasRole('USER')")
    @Operation(operationId = "USR005", summary = "일상존 변경")
    fun updateAreaZone(
        @AuthenticationPrincipal auth: AuthenticationModel,
        @RequestBody request: UpdateUserAreaZoneRequest,
    ): ResponseEntity<UserInfoResponse> {
        val user = userService.updateAreaZone(auth.userId, request.commercialAreaCode)

        return ResponseEntity.ok(UserInfoResponse.from(user))
    }

}
