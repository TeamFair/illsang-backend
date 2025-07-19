package com.illsang.auth.model

data class RefreshTokenData(
    val accessToken: String,
    val refreshToken: String
)
