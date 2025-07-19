package com.illsang.user.dto.response

import com.illsang.auth.model.TokenModel

data class LoginResponse(
    val accessToken: String,
    val refreshToken: String
) {
    companion object {
        fun from(token: TokenModel): LoginResponse {
            return LoginResponse(
                accessToken = token.accessToken,
                refreshToken = token.refreshToken,
            )
        }
    }
}
