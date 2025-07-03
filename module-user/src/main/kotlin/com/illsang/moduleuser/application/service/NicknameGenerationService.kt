package com.illsang.moduleuser.application.service

import com.illsang.moduleuser.application.port.out.UserPersistencePort
import org.springframework.stereotype.Service
import kotlin.random.Random

@Service
class NicknameGenerationService(
    private val userPersistencePort: UserPersistencePort
) {

    companion object {
        private const val MAX_ATTEMPTS = 100
    }

    fun generateUniqueNickname(): String {
        var attempts = 0

        while (attempts < MAX_ATTEMPTS) {
            val randomNumber = generateRandomNumber()
            val nickname = "일상${String.format("%08d", randomNumber)}"

            if (!userPersistencePort.existsByNickname(nickname)) {
                return nickname
            }

            attempts++
        }

        throw IllegalStateException("Unable to generate unique nickname after $MAX_ATTEMPTS attempts")
    }

    private fun generateRandomNumber(): Int {
        val number = Random.nextInt(1, 100000000) // 8-digit number (10000000 to 99999999)
        return number
    }
}
