package com.illsang.auth.domain.model

data class RefreshTokenData(
    val accessToken: String,
    val refreshToken: String
)
