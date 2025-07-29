package com.illsang.auth.service

import com.illsang.auth.enums.OAuthProvider
import com.illsang.auth.enums.OSType
import com.illsang.auth.model.AuthenticationModel
import com.illsang.auth.model.OAuthLogin
import com.illsang.auth.model.RefreshTokenData
import com.illsang.auth.model.TokenModel
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

    fun createByRefreshToken(userId: String, request: RefreshTokenData): TokenModel {
        val storedRefreshTokenData = redisTokenService.getRefreshTokenData(userId)
            ?: throw IllegalArgumentException("No refresh token found for user")

        // Compare tokens with stored ones
        if (storedRefreshTokenData.accessToken != request.accessToken ||
            storedRefreshTokenData.refreshToken != request.refreshToken
        ) {
            throw IllegalArgumentException("Invalid tokens")
        }

        // Generate new tokens
        return generateAndStoreTokens(userId)
    }

    fun generateAndStoreTokens(userId: String): TokenModel {
        // Generate tokens
        val accessToken = jwtTokenService.generateAccessToken(userId)
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
        val osType = OSType.fromString(request.osType)

        val provider = OAuthProvider.fromString(request.provider)

        return when (provider) {
            OAuthProvider.GOOGLE -> {
                val payload = this.googleTokenValidationService.validateIdToken(request.idToken, osType)

                mapOf(
                    "email" to payload.email,
                )
            }

            OAuthProvider.APPLE -> {
                val claims = this.appleTokenValidationService.validateIdToken(request.idToken, osType)

                mapOf(
                    "email" to (claims["email"] as? String ?: ""),
                )
            }
        }
    }
}
