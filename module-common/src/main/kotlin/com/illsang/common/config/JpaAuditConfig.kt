package com.illsang.common.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.AuditorAware
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import java.util.*

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
class JpaAuditConfig {

    @Bean
    fun auditorProvider(): AuditorAware<String> {
        return AuditorAware {
            val authentication: Authentication? = SecurityContextHolder.getContext().authentication
            if (authentication != null && authentication.isAuthenticated) {
                Optional.of(authentication.name)
            } else {
                Optional.of("SYSTEM")
            }
        }
    }
} 