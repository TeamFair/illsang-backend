package com.illsang.common.adapter.out.persistence

import com.illsang.common.application.port.out.TokenPersistencePort
import org.springframework.core.env.Environment
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component

@Component
class RedisTokenPersistenceAdapter(
    private val redisTemplate: RedisTemplate<String, String>,
    private val environment: Environment
) : TokenPersistencePort {

    private val environmentPrefix: String by lazy {
        environment.activeProfiles.firstOrNull() ?: "default"
    }

    private val TOKEN_KEY_PREFIX = "token:$environmentPrefix:"

    override fun getAccessToken(userId: String): String? {
        val key = "$TOKEN_KEY_PREFIX$userId"
        return redisTemplate.opsForValue().get(key)
    }
}
