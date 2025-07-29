package com.illsang.auth.filter

import com.illsang.auth.domain.model.AuthenticationModel
import com.illsang.auth.service.AuthenticationService
import com.illsang.auth.service.AuthUserService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

class AuthenticationFilter(
    private val authenticationService: AuthenticationService,
    private val userService: AuthUserService,
) : OncePerRequestFilter() {

    public override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authHeader = request.getHeader("authorization")

        if (authHeader != null) {
            val token = authHeader.replace("Bearer", "", true).trim()
            var authModel = extractAuthenticationFromContext()

            val authentication = if (authModel.isAuthenticated()) {
                val isValid = authenticationService.validateAuthentication(authModel, token)
                if (!isValid) {
                    throw BadCredentialsException("Invalid or expired token.")
                }

                 UsernamePasswordAuthenticationToken(authModel, null, authModel.authorities)
            } else {
                val email = getSwaggerLoginEmail(token)
                val user = this.userService.getUserByEmail(email) ?: throw BadCredentialsException("User not found.")

                authModel = if (user.roles.contains("ADMIN")) {
                    AuthenticationModel.authenticated(user.id!!, user.roles)
                } else {
                    AuthenticationModel.unauthenticated()
                }

                UsernamePasswordAuthenticationToken(authModel, null, authModel.authorities)
            }

            SecurityContextHolder.getContext().authentication = authentication
        }

        filterChain.doFilter(request, response)
    }

    private fun getSwaggerLoginEmail(googleToken: String): String {
        val tokenInfo = authenticationService.getGoogleImplicitTokenInfo(googleToken)

        return tokenInfo["email"] ?: throw BadCredentialsException("Invalid email.")
    }

    private fun extractAuthenticationFromContext(): AuthenticationModel {
        return try {
            val authentication = SecurityContextHolder.getContext().authentication
            if (authentication is JwtAuthenticationToken) {
                AuthenticationModel.authenticated(authentication)
            } else {
                AuthenticationModel.unauthenticated()
            }
        } catch (e: Exception) {
            AuthenticationModel.unauthenticated()
        }
    }
}
