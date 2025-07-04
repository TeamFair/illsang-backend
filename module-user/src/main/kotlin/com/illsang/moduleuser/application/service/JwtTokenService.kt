package com.illsang.moduleuser.application.service

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.security.Key
import java.util.*
import javax.crypto.SecretKey

@Service
class JwtTokenService {

    @Value("\${oauth.token.timeout.access-token}")
    private val accessTokenExpirationMinutes: Long = 1

    @Value("\${oauth.token.timeout.refresh-token}")
    private val refreshTokenExpirationMinutes: Long = 2

    private val key: SecretKey = Jwts.SIG.HS256.key().build()

    companion object {
        private const val MINUTES_TO_MILLISECONDS = 60 * 1000L
    }

    fun generateAccessToken(userId: Long): String {
        val now = Date()
        val expiryDate = Date(now.time + accessTokenExpirationMinutes * MINUTES_TO_MILLISECONDS)

        return Jwts.builder()
            .subject(userId.toString())
            .issuedAt(now)
            .expiration(expiryDate)
            .signWith(key)
            .compact()
    }

    fun generateRefreshToken(userId: Long): String {
        val now = Date()
        val expiryDate = Date(now.time + refreshTokenExpirationMinutes * MINUTES_TO_MILLISECONDS)

        return Jwts.builder()
            .subject(userId.toString())
            .issuedAt(now)
            .expiration(expiryDate)
            .signWith(key)
            .compact()
    }

    fun getAccessTokenExpirationMinutes(): Long = accessTokenExpirationMinutes
    fun getRefreshTokenExpirationMinutes(): Long = refreshTokenExpirationMinutes

    fun getUserIdFromToken(token: String): Long {
        val claims = Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .payload

        return claims.subject.toLong()
    }
}
