package com.illsang.auth.model

import com.illsang.auth.enums.UserType

data class AuthenticationModel(
    val userType: UserType = UserType.UNKNOWN,
    val userId: String,
    val group: String? = null,
) {
    fun isAuthenticated(): Boolean = userType != UserType.UNKNOWN

    fun hasGroup(): Boolean = !group.isNullOrEmpty()

    companion object {
        fun unauthenticated(): AuthenticationModel = AuthenticationModel(userId = "")

        fun authenticated(userId: String, userType: UserType, group: String? = null): AuthenticationModel =
            AuthenticationModel(userType, userId, group)
    }
}
