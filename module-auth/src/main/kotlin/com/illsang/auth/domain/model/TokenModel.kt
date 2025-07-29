package com.illsang.auth.domain.model

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
}
