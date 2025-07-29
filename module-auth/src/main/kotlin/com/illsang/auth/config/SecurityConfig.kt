package com.illsang.auth.config

import com.illsang.auth.filter.AuthenticationFilter
import com.illsang.auth.service.AuthUserService
import com.illsang.auth.service.AuthenticationService
import com.illsang.auth.service.JwtTokenService
import org.springframework.boot.autoconfigure.security.servlet.PathRequest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.oauth2.jose.jws.MacAlgorithm
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.JwtException
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.access.intercept.AuthorizationFilter
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.time.Instant

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
class SecurityConfig(
    private val jwtTokenService: JwtTokenService,
    private val authenticationService: AuthenticationService,
    private val userService: AuthUserService,
) {
    private val authenticationFilter = AuthenticationFilter(authenticationService, userService)

    @Bean
    fun webSecurityCustomizer(): WebSecurityCustomizer {
        // 정적 자원에 대해서 Security를 적용하지 않음으로 설정
        return WebSecurityCustomizer { web: WebSecurity ->
            web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
        }
    }

    @Bean
    @Order(1) // 먼저 실행
    fun publicSecurityFilterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            // 🔥 중요: Public 경로만 매칭
            .securityMatcher(
                "/h2-console/**",
                "/api/v1/open/**",
                "/swagger-ui/**",
                "/v3/api-docs/**"
            )
            .csrf { it.disable() }
            .cors { it.disable() }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authorizeHttpRequests { authz ->
                authz.anyRequest().permitAll() // 매칭된 모든 경로 허용
            }
            .headers { headers ->
                headers.frameOptions { it.sameOrigin() } // H2 Console용
            }
            // 🔥 중요: JWT 관련 필터를 추가하지 않음!
            .build()
    }

    // 🔥 Protected 경로용 SecurityFilterChain (JWT 필터 있음)
    @Bean
    @Order(2) // 나중에 실행
    fun protectedSecurityFilterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            // 🔥 중요: Public 경로를 제외한 나머지 모든 경로
            .securityMatcher("/**")
            .csrf { it.disable() }
            .cors { it.disable() }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authorizeHttpRequests { authz ->
                authz.anyRequest().authenticated() // 모든 요청 인증 필요
            }
            .headers { headers ->
                headers.frameOptions { it.sameOrigin() }
            }
            // 🔥 인증이 필요한 경로에만 JWT 필터 적용
            .oauth2ResourceServer { oauth2 ->
                oauth2.jwt { jwt ->
                    jwt.decoder(jwtDecoder())
                }
            }
            .addFilterAfter(authenticationFilter, BearerTokenAuthenticationFilter::class.java)
            .build()
    }

    @Bean
    fun jwtDecoder(): JwtDecoder {
        return JwtDecoder {token ->
            try {
                val decoder = NimbusJwtDecoder
                    .withSecretKey(jwtTokenService.getSecretKey())
                    .macAlgorithm(MacAlgorithm.HS384)
                    .build()
                decoder.decode(token)
            } catch (e: JwtException) {
                Jwt.withTokenValue(token)
                    .header("alg", "none")
                    .header("typ", "PASS_THROUGH")
                    .claim("sub", "unknown")
                    .claim("pass_through", true)
                    .claim("original_token", token)
                    .issuedAt(Instant.now())
                    .expiresAt(Instant.now().plusSeconds(1))
                    .build()
            }

        }
    }

    @Bean
    fun jwtAuthenticationConverter(): JwtAuthenticationConverter {
        val grantedAuthoritiesConverter = JwtGrantedAuthoritiesConverter()
        grantedAuthoritiesConverter.setAuthorityPrefix("")

        val authConverter = JwtAuthenticationConverter()
        authConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter)
        return authConverter
    }

}
