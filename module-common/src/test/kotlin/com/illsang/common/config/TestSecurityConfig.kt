package com.illsang.common.config

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.jwt.JwtDecoder
import java.time.Instant

@TestConfiguration
class TestSecurityConfig {

    @Bean
    @Primary
    fun mockJwtDecoder(): JwtDecoder {
        return JwtDecoder { token ->
            // Create a mock JWT that matches what the tests expect
            Jwt.withTokenValue(token)
                .header("alg", "RS256")
                .subject("user123")
                .claim("authorities", listOf("ROLE_CUSTOMER"))
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(3600))
                .build()
        }
    }
}
