package com.illsang.moduleuser.adapter.`in`.web

import com.illsang.moduleuser.adapter.`in`.web.model.request.OAuthLoginRequest
import com.illsang.moduleuser.adapter.`in`.web.model.response.OAuthLoginResponse
import com.illsang.moduleuser.application.service.AuthService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1")
class AuthController(
    private val authService: AuthService
) {

    @PostMapping("/open/oauth/login")
    fun oauthLogin(@RequestBody request: OAuthLoginRequest): ResponseEntity<OAuthLoginResponse> {
        val response = authService.processOAuthLogin(request)

        return ResponseEntity.ok(response)
    }
}
