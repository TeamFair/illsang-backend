package com.illsang.auth.service

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.*
import javax.crypto.SecretKey

@Service
class JwtTokenService {

    @Value("\${oauth.token.timeout.access-token}")
    private val accessTokenExpirationMinutes: Long = 1

    @Value("\${oauth.token.timeout.refresh-token}")
    private val refreshTokenExpirationMinutes: Long = 2

    @Value("\${jwt.token}")
    private lateinit var secretKey: String

    private val key: SecretKey by lazy {
        val keyBytes = Base64.getDecoder().decode(secretKey)
        Keys.hmacShaKeyFor(keyBytes)
    }

    fun generateAccessToken(userId: String, roles: List<String> = emptyList()): String {
        val now = Date.from(Instant.now())
        val expiryDate = Date.from(Instant.now().plus(accessTokenExpirationMinutes, ChronoUnit.MINUTES))

        val authorities = roles.map { "ROLE_$it" }

        return Jwts.builder()
            .subject(userId)
            .claim("scope", authorities)
            .issuedAt(now)
            .expiration(expiryDate)
            .signWith(key)
            .compact()
    }

    fun generateRefreshToken(userId: String): String {
        val now = Date.from(Instant.now())
        val expiryDate = Date.from(Instant.now().plus(refreshTokenExpirationMinutes, ChronoUnit.MINUTES))

        return Jwts.builder()
            .subject(userId)
            .issuedAt(now)
            .expiration(expiryDate)
            .signWith(key)
            .compact()
    }

    fun getAccessTokenExpirationMinutes(): Long = accessTokenExpirationMinutes
    fun getRefreshTokenExpirationMinutes(): Long = refreshTokenExpirationMinutes

    fun getUserIdFromToken(token: String): String {
        try {
            val claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .payload

            return claims.subject
        } catch (e: Exception) {
            throw IllegalArgumentException("Token is invalid")
        }
    }

    fun getSecretKey(): SecretKey = key

}
