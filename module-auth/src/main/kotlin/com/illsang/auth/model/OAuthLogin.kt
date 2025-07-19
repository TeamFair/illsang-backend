package com.illsang.auth.model

data class OAuthLogin(
    val provider: String,
    val osType: String,
    val idToken: String
)
