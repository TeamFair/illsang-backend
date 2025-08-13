package com.illsang.common.util

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

object PasswordUtil {
    private val encoder = BCryptPasswordEncoder()

    /**
     * 비밀번호 단방향 암호화
     * @param rawPassword 원문 비밀번호
     * @return 암호화된 비밀번호
     */
    fun encode(rawPassword: String): String {
        return encoder.encode(rawPassword)
    }

    /**
     * 입력한 비밀번호가 저장된 해시와 일치하는지 검증
     * @param rawPassword 원문 비밀번호
     * @param encodedPassword 저장된 해시 비밀번호
     * @return true = 일치, false = 불일치
     */
    fun matches(rawPassword: String, encodedPassword: String): Boolean {
        return encoder.matches(rawPassword, encodedPassword)
    }
}