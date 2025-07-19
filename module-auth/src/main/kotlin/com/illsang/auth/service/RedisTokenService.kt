package com.illsang.auth.service

import com.illsang.auth.constants.TokenConstants
import com.illsang.auth.model.RefreshTokenData
import org.springframework.core.env.Environment
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit

@Service
class RedisTokenService(
    private val redisTemplate: RedisTemplate<String, String>,
    private val environment: Environment
) {

    private val environmentPrefix: String by lazy {
        environment.activeProfiles.firstOrNull() ?: "default"
    }

    private final val TOKEN_KEY_PREFIX = "token:$environmentPrefix:"
    private final val REFRESH_TOKEN_KEY_PREFIX = "refresh_token:$environmentPrefix:"

    fun getAccessToken(userId: String): String? {
        val key = "$TOKEN_KEY_PREFIX$userId"
        return redisTemplate.opsForValue().get(key)
    }

    fun storeAccessToken(userId: String, accessToken: String, expirationMinutes: Long) {
        val key = "$TOKEN_KEY_PREFIX$userId"
        redisTemplate.opsForValue().set(key, accessToken, expirationMinutes, TimeUnit.MINUTES)
    }

    fun storeRefreshToken(userId: String, accessToken: String, refreshToken: String, expirationMinutes: Long) {
        val key = "$REFRESH_TOKEN_KEY_PREFIX$userId"
        val tokenData = mapOf(
            TokenConstants.ACCESS_TOKEN_KEY to accessToken,
            TokenConstants.REFRESH_TOKEN_KEY to refreshToken
        )
        redisTemplate.opsForHash<String, String>().putAll(key, tokenData)
        redisTemplate.expire(key, expirationMinutes, TimeUnit.MINUTES)
    }

    fun getRefreshTokenData(userId: String): RefreshTokenData? {
        val key = "$REFRESH_TOKEN_KEY_PREFIX$userId"
        val data = redisTemplate.opsForHash<String, String>().entries(key)
        return if (data.isEmpty()) {
            null
        } else {
            RefreshTokenData(
                accessToken = data[TokenConstants.ACCESS_TOKEN_KEY] ?: "",
                refreshToken = data[TokenConstants.REFRESH_TOKEN_KEY] ?: ""
            )
        }
    }

    fun deleteTokens(userId: String) {
        val accessTokenKey = "$TOKEN_KEY_PREFIX$userId"
        val refreshTokenKey = "$REFRESH_TOKEN_KEY_PREFIX$userId"
        redisTemplate.delete(listOf(accessTokenKey, refreshTokenKey))
    }
}
