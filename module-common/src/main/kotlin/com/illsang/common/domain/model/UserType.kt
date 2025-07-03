package com.illsang.common.domain.model

enum class UserType(
) {
    BOSS,
    CUSTOMER,
    ADMIN,
    UNKNOWN,
    ;

    companion object {
        fun fromString(value: String): UserType {
            return requireNotNull(UserType.entries.find { it.name.equals(value, ignoreCase = true) }) {
                "No enum constant UserType for string: $value"
            }
        }
    }
}
