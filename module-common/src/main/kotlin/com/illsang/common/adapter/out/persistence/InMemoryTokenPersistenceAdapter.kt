package com.illsang.common.adapter.out.persistence

import com.illsang.common.application.port.out.TokenPersistencePort
import com.illsang.common.domain.model.TokenModel
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import java.util.concurrent.ConcurrentHashMap

@Component
@Profile("local")
class InMemoryTokenPersistenceAdapter : TokenPersistencePort {

    private val tokenStore: MutableMap<String, String> = ConcurrentHashMap()
    private val refreshTokenStore: MutableMap<String, Map<String, String>> = ConcurrentHashMap()

    override fun saveToken(userId: String, tokenModel: TokenModel) {
        tokenStore[userId] = tokenModel.accessToken

        val refreshTokenMap = mapOf(
            "accessToken" to tokenModel.accessToken,
            "refreshToken" to tokenModel.refreshToken
        )
        refreshTokenStore[userId] = refreshTokenMap
    }

    override fun getAccessToken(userId: String): String? {
        return tokenStore[userId]
    }

    override fun getTokenModel(userId: String): TokenModel? {
        val tokenMap = refreshTokenStore[userId] ?: return null
        return try {
            TokenModel.fromTokenMap(tokenMap)
        } catch (e: IllegalArgumentException) {
            null
        }
    }

    override fun deleteToken(userId: String) {
        tokenStore.remove(userId)
        refreshTokenStore.remove(userId)
    }

    override fun deleteAllTokens() {
        tokenStore.clear()
        refreshTokenStore.clear()
    }
}
