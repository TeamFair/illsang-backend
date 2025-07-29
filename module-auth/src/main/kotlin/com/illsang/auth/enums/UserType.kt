package com.illsang.auth.enums

enum class UserType(
) {
    BOSS,
    USER,
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
