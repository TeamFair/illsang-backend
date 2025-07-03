package com.illsang.common.domain.model

data class AuthenticationModel(
    val userType: UserType = UserType.UNKNOWN,
    val userId: String? = null,
    val group: String? = null,
) {
    fun isAuthenticated(): Boolean = userId != null && userType != UserType.UNKNOWN

    fun hasGroup(): Boolean = !group.isNullOrEmpty()

    companion object {
        fun unauthenticated(): AuthenticationModel = AuthenticationModel()

        fun authenticated(userId: String, userType: UserType, group: String? = null): AuthenticationModel =
            AuthenticationModel(userType, userId, group)
    }
}
