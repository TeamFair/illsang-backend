package com.illsang.moduleuser.adapter.`in`.web.model.request

data class OAuthLoginRequest(
    val provider: String,
    val osType: String,
    val idToken: String
)
