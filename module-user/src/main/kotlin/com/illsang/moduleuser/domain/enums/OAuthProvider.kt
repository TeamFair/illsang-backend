package com.illsang.moduleuser.domain.enums

enum class OAuthProvider {
    GOOGLE,
    APPLE;

    companion object {
        fun fromString(value: String): OAuthProvider {
            return requireNotNull(OAuthProvider.entries.find { it.name.equals(value, ignoreCase = true) }) {
                "Unsupported OAuth provider : $value"
            }
        }
    }
}
