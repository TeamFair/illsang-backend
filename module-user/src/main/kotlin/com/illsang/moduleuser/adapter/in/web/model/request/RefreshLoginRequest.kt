package com.illsang.moduleuser.adapter.`in`.web.model.request

data class RefreshLoginRequest(
    val accessToken: String,
    val refreshToken: String,
)
