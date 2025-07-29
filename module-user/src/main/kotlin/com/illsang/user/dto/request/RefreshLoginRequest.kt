package com.illsang.user.dto.request

data class RefreshLoginRequest(
    val accessToken: String,
    val refreshToken: String,
)
