package com.illsang.common.domain.model

data class TokenModel(
    val accessToken: String,
    val refreshToken: String,
) {
    fun isValid(providedRefreshToken: String, providedAccessToken: String): Boolean {
        return refreshToken == providedRefreshToken && accessToken == providedAccessToken
    }

    fun isAccessTokenValid(providedAccessToken: String): Boolean {
        return accessToken == providedAccessToken
    }

    fun isRefreshTokenValid(providedRefreshToken: String): Boolean {
        return refreshToken == providedRefreshToken
    }

    companion object {
        fun fromTokenMap(tokenMap: Map<String, String>): TokenModel {
            return TokenModel(
                accessToken = tokenMap["accessToken"] ?: throw IllegalArgumentException("Access token is required"),
                refreshToken = tokenMap["refreshToken"] ?: throw IllegalArgumentException("Refresh token is required")
            )
        }

        fun create(accessToken: String, refreshToken: String): TokenModel {
            require(accessToken.isNotBlank()) { "Access token cannot be blank" }
            require(refreshToken.isNotBlank()) { "Refresh token cannot be blank" }
            return TokenModel(accessToken, refreshToken)
        }
    }
}
