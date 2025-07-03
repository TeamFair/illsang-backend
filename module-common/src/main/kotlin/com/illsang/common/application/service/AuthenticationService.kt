package com.illsang.common.application.service

import com.illsang.common.application.port.out.TokenPersistencePort
import com.illsang.common.domain.model.AuthenticationModel
import org.springframework.stereotype.Service

@Service
class AuthenticationService(
    private val tokenPersistencePort: TokenPersistencePort,
) {

    fun isTokenValid(userId: String, token: String): Boolean {
        val storedToken = tokenPersistencePort.getAccessToken(userId)
        return storedToken == token
    }

    fun validateAuthentication(authentication: AuthenticationModel, token: String): Boolean {
        if (!authentication.isAuthenticated()) {
            return false
        }

        return authentication.userId?.let { userId ->
            isTokenValid(userId, token)
        } ?: false
    }
}
