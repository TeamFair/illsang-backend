package com.illsang.user.dto.request

import com.illsang.auth.domain.model.OAuthLogin
import com.illsang.auth.enums.OAuthProvider
import com.illsang.auth.enums.OSType

data class OAuthLoginRequest(
    val provider: OAuthProvider,
    val osType: OSType,
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
