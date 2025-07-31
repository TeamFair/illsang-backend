package com.illsang.auth.service

import com.illsang.auth.domain.model.AuthenticationModel
import com.illsang.auth.domain.model.OAuthLogin
import com.illsang.auth.domain.model.RefreshTokenData
import com.illsang.auth.domain.model.TokenModel
import com.illsang.auth.enums.OAuthProvider
import org.springframework.stereotype.Service

@Service
class AuthenticationService(
    private val redisTokenService: RedisTokenService,
    private val jwtTokenService: JwtTokenService,
    private val googleTokenValidationService: GoogleTokenValidationService,
    private val appleTokenValidationService: AppleTokenValidationService,
) {

    fun isTokenValid(userId: String, token: String): Boolean {
        val storedToken = redisTokenService.getAccessToken(userId)
        return storedToken == token
    }

    fun validateAuthentication(authentication: AuthenticationModel, token: String): Boolean {
        if (!authentication.isAuthenticated()) {
            return false
        }

        return isTokenValid(authentication.userId, token)
    }

    fun createByRefreshToken(userId: String, request: RefreshTokenData, roles: List<String> = emptyList()): TokenModel {
        val storedRefreshTokenData = redisTokenService.getRefreshTokenData(userId)
            ?: throw IllegalArgumentException("No refresh token found for user")

        // Compare tokens with stored ones
        if (storedRefreshTokenData.accessToken != request.accessToken ||
            storedRefreshTokenData.refreshToken != request.refreshToken
        ) {
            throw IllegalArgumentException("Invalid tokens")
        }

        // Generate new tokens
        return generateAndStoreTokens(userId, roles)
    }

    fun generateAndStoreTokens(userId: String, roles: List<String> = emptyList()): TokenModel {
        // Generate tokens
        val accessToken = jwtTokenService.generateAccessToken(userId, roles)
        val refreshToken = jwtTokenService.generateRefreshToken(userId)

        // Store tokens in Redis
        redisTokenService.storeAccessToken(
            userId,
            accessToken,
            jwtTokenService.getAccessTokenExpirationMinutes()
        )

        redisTokenService.storeRefreshToken(
            userId,
            accessToken,
            refreshToken,
            jwtTokenService.getRefreshTokenExpirationMinutes()
        )

        return TokenModel(
            accessToken = accessToken,
            refreshToken = refreshToken
        )
    }

    fun deleteToken(userId: String) {
        this.redisTokenService.deleteTokens(userId)
    }



    fun validateOAuthToken(request: OAuthLogin): Map<String, String> {
        return when (request.provider) {
            OAuthProvider.GOOGLE -> {
                val payload = this.googleTokenValidationService.validateIdToken(request.idToken, request.osType)

                mapOf(
                    "email" to payload.email,
                )
            }

            OAuthProvider.APPLE -> {
                val claims = this.appleTokenValidationService.validateIdToken(request.idToken, request.osType)

                mapOf(
                    "email" to (claims["email"] as? String ?: ""),
                )
            }
        }
    }

    fun getGoogleImplicitTokenInfo(accessToken: String): Map<String, String> {
        return googleTokenValidationService.getGoogleImplicitTokenInfo(accessToken)
    }

}
