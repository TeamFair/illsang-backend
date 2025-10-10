package com.illsang.auth.enums

enum class OSType {
    IOS,
    AOS,
    WEB,
    ;

    companion object {
        fun fromString(value: String): OSType {
            return requireNotNull(OSType.entries.find { it.name.equals(value, ignoreCase = true) }) {
                "Unsupported OS type : $value"
            }
        }
    }
}
