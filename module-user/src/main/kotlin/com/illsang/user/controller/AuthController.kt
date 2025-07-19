package com.illsang.user.controller

import com.illsang.auth.model.AuthenticationModel
import com.illsang.user.dto.request.OAuthLoginRequest
import com.illsang.user.dto.request.RefreshLoginRequest
import com.illsang.user.dto.response.LoginResponse
import com.illsang.user.service.AuthService
import jakarta.annotation.security.PermitAll
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1")
class AuthController(
    private val authService: AuthService
) {

    @PostMapping("/open/login/oauth")
    fun oauthLogin(@RequestBody request: OAuthLoginRequest): ResponseEntity<LoginResponse> {
        val response = authService.oauthLogin(request)

        return ResponseEntity.ok(response)
    }

    @PostMapping("/open/login/refresh")
    fun refreshLogin(@RequestBody request: RefreshLoginRequest): ResponseEntity<LoginResponse> {
        val response = authService.refreshLogin(request)

        return ResponseEntity.ok(response)
    }

    @PostMapping("/logout")
    @PermitAll
    fun logout(@AuthenticationPrincipal authenticationModel: AuthenticationModel): ResponseEntity<Void> {
        authService.logout(authenticationModel)

        return ResponseEntity.ok().build()
    }

    @PostMapping("/withdraw")
    fun withdraw(
        @AuthenticationPrincipal authenticationModel: AuthenticationModel,
    ): ResponseEntity<Void> {
        authService.withdraw(authenticationModel)

        return ResponseEntity.ok().build()
    }

}
