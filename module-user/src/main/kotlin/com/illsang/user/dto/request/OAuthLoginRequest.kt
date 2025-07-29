package com.illsang.user.dto.request

import com.illsang.auth.model.OAuthLogin

data class OAuthLoginRequest(
    val provider: String,
    val osType: String,
    val idToken: String
) {
    companion object {
        fun toOAuthLogin(request: OAuthLoginRequest): OAuthLogin {
            return OAuthLogin(
                provider = request.provider,
                osType = request.osType,
                idToken = request.idToken,
            )
        }
    }
}
