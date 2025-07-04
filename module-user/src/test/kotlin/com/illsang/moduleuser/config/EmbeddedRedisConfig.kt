package com.illsang.moduleuser.config

import jakarta.annotation.PostConstruct
import jakarta.annotation.PreDestroy
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.StringRedisSerializer
import redis.embedded.RedisServer
import java.net.ServerSocket

@Configuration
class EmbeddedRedisConfig {

    @Value("\${spring.data.redis.host:localhost}")
    private lateinit var redisHost: String

    private var redisServer: RedisServer? = null
    private var redisPort: Int = 0

    @PostConstruct
    fun startRedis() {
        redisPort = findAvailablePort()
        redisServer = RedisServer(redisPort)
        redisServer?.start()

        // Update system property so other components can use the dynamic port
        System.setProperty("spring.data.redis.port", redisPort.toString())
    }

    @PreDestroy
    fun stopRedis() {
        redisServer?.stop()
    }

    @Bean
    @Primary
    fun embeddedRedisConnectionFactory(): RedisConnectionFactory {
        val redisConfiguration = RedisStandaloneConfiguration(redisHost, redisPort)
        return LettuceConnectionFactory(redisConfiguration)
    }

    @Bean
    @Primary
    fun embeddedRedisTemplate(): RedisTemplate<String, String> {
        val template = RedisTemplate<String, String>()
        template.connectionFactory = embeddedRedisConnectionFactory()
        template.keySerializer = StringRedisSerializer()
        template.valueSerializer = StringRedisSerializer()
        return template
    }

    private fun findAvailablePort(): Int {
        return try {
            val socket = ServerSocket(0)
            val port = socket.localPort
            socket.close()
            port
        } catch (e: Exception) {
            6370 // fallback port
        }
    }
}
