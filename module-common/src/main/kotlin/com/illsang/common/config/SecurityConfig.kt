package com.illsang.common.config

import com.illsang.common.adapter.`in`.filter.AuthenticationFilter
import org.springframework.boot.autoconfigure.security.servlet.PathRequest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
class SecurityConfig(
    private val authenticationFilter: AuthenticationFilter,
) {

    @Bean
    fun webSecurityCustomizer(): WebSecurityCustomizer {
        // 정적 자원에 대해서 Security를 적용하지 않음으로 설정
        return WebSecurityCustomizer { web: WebSecurity ->
            web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
        }
    }

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain? {
        http.csrf { it.disable() }
            .cors { it.disable() }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authorizeHttpRequests { authz ->
                authz.requestMatchers("/h2-console/**").permitAll()  // H2 Console for development
                    .requestMatchers("/open/**").permitAll()
                    .anyRequest().authenticated()  // All other requests require authentication, authorization handled by method-level annotations
            }
            .headers { headers -> headers.frameOptions { it.sameOrigin() } }
            .oauth2ResourceServer { oauth2 -> oauth2.jwt { } }
            .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter::class.java)

        return http.build()
    }
}
