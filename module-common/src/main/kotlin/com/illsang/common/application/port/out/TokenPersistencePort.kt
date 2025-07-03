package com.illsang.common.application.port.out

import com.illsang.common.domain.model.RefreshTokenData

interface TokenPersistencePort {
    fun getAccessToken(userId: String): String?
    fun storeAccessToken(userId: String, accessToken: String, expirationMinutes: Long)
    fun storeRefreshToken(userId: String, accessToken: String, refreshToken: String, expirationMinutes: Long)
    fun getRefreshTokenData(userId: String): RefreshTokenData?
    fun deleteTokens(userId: String)
}
