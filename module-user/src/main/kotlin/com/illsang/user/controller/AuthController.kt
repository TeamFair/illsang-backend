package com.illsang.user.controller

import com.illsang.auth.domain.model.AuthenticationModel
import com.illsang.user.dto.request.OAuthLoginRequest
import com.illsang.user.dto.request.RefreshLoginRequest
import com.illsang.user.dto.response.LoginResponse
import com.illsang.user.service.AuthService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.annotation.security.PermitAll
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Auth", description = "로그인")
class AuthController(
    private val authService: AuthService
) {

    @PostMapping("/open/login/oauth")
    @Operation(operationId = "AUH001", summary= "Oauth 로그인")
    fun oauthLogin(@RequestBody request: OAuthLoginRequest): ResponseEntity<LoginResponse> {
        val response = authService.oauthLogin(request)

        return ResponseEntity.ok(response)
    }

    @PostMapping("/open/login/refresh")
    @Operation(operationId = "AUH002", summary= "RefreshToken 로그인")
    fun refreshLogin(@RequestBody request: RefreshLoginRequest): ResponseEntity<LoginResponse> {
        val response = authService.refreshLogin(request)

        return ResponseEntity.ok(response)
    }

    @PostMapping("/logout")
    @PreAuthorize("hasRole('USER')")
    @Operation(operationId = "AUH003", summary= "로그아웃")
    fun logout(@AuthenticationPrincipal authenticationModel: AuthenticationModel): ResponseEntity<Void> {
        authService.logout(authenticationModel)

        return ResponseEntity.ok().build()
    }

    @PostMapping("/withdraw")
    @PreAuthorize("hasRole('USER')")
    @Operation(operationId = "AUH004", summary= "회원탈퇴")
    fun withdraw(
        @AuthenticationPrincipal authenticationModel: AuthenticationModel,
    ): ResponseEntity<Void> {
        authService.withdraw(authenticationModel)

        return ResponseEntity.ok().build()
    }

}
