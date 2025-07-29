package com.illsang.auth.domain.model

import com.illsang.auth.enums.OAuthProvider
import com.illsang.auth.enums.OSType

data class OAuthLogin(
    val provider: OAuthProvider,
    val osType: OSType,
    val idToken: String
)
