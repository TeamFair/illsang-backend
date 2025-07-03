package com.illsang.common.adapter.`in`.filter

import com.illsang.common.application.service.AuthenticationService
import com.illsang.common.domain.model.AuthenticationModel
import com.illsang.common.domain.model.UserType
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class AuthenticationFilter(
    private val authenticationService: AuthenticationService
) : OncePerRequestFilter() {

    public override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authentication = extractAuthenticationFromContext()
        if (authentication.isAuthenticated()) {
            val token = request.getHeader("authorization")
                ?: throw BadCredentialsException("Token is required")

            val isValid = authenticationService.validateAuthentication(authentication, token)
            if (!isValid) {
                throw BadCredentialsException("Invalid or expired token.")
            }
        }

        filterChain.doFilter(request, response)
    }

    /**
     * SecurityContextHolder에서 JWT를 가져와 AuthenticationModel로 변환
     */
    private fun extractAuthenticationFromContext(): AuthenticationModel {
        return try {
            val authentication = SecurityContextHolder.getContext().authentication
            if (authentication?.principal is Jwt) {
                val jwt = authentication.principal as Jwt
                createAuthenticationModel(jwt)
            } else {
                AuthenticationModel.unauthenticated()
            }
        } catch (e: Exception) {
            AuthenticationModel.unauthenticated()
        }
    }

    /**
     * JWT에서 AuthenticationModel 객체를 생성하는 헬퍼 함수
     */
    private fun createAuthenticationModel(jwt: Jwt): AuthenticationModel {
        val userId = jwt.subject
        val authorities = jwt.getClaimAsStringList("authorities") ?: emptyList()

        // authorities에서 userType과 group 추출
        val userTypeRole = authorities.find { it.startsWith("ROLE_") &&
                listOf("ROLE_BOSS", "ROLE_CUSTOMER", "ROLE_ADMIN").contains(it) }
        val userType = userTypeRole?.removePrefix("ROLE_")?.let { UserType.fromString(it) } ?: UserType.UNKNOWN

        val group = authorities.find { it.startsWith("ROLE_") && it != userTypeRole }?.removePrefix("ROLE_")

        return if (userId != null && userType != UserType.UNKNOWN) {
            AuthenticationModel.authenticated(userId, userType, group)
        } else {
            AuthenticationModel.unauthenticated()
        }
    }
}
