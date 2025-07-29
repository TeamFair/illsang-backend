package com.illsang.auth.domain.model

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken

data class AuthenticationModel(
    val userId: String,
    val authorities: List<GrantedAuthority> = emptyList()
) : UserDetails {

    companion object {
        fun unauthenticated(): AuthenticationModel = AuthenticationModel(userId = "")
        fun authenticated(userId: String, roles: List<String>): AuthenticationModel {
            val authorities = roles.map { SimpleGrantedAuthority(if (it.startsWith("ROLE_")) it else "ROLE_$it") }

            return AuthenticationModel(
                userId = userId,
                authorities = authorities
            )
        }

        fun authenticated(jwtToken: JwtAuthenticationToken): AuthenticationModel {
            return AuthenticationModel(
                userId = jwtToken.name,
                authorities = jwtToken.authorities.toList(),
            )
        }

    }

    fun isAuthenticated(): Boolean = userId.isNotEmpty() && authorities.isNotEmpty()

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return authorities
    }

    override fun getPassword(): String? {
        return null
    }

    override fun getUsername(): String {
        return userId
    }

}
