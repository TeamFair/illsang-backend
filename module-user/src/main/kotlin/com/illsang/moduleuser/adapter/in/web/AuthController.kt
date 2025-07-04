package com.illsang.moduleuser.adapter.`in`.web

import com.illsang.common.domain.model.AuthenticationModel
import com.illsang.moduleuser.adapter.`in`.web.model.request.OAuthLoginRequest
import com.illsang.moduleuser.adapter.`in`.web.model.request.RefreshLoginRequest
import com.illsang.moduleuser.adapter.`in`.web.model.response.LoginResponse
import com.illsang.moduleuser.application.service.AuthService
import jakarta.annotation.security.PermitAll
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

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
}
