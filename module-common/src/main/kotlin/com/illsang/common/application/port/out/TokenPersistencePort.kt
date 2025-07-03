package com.illsang.common.application.port.out

interface TokenPersistencePort {
    fun getAccessToken(userId: String): String?
}
