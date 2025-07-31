package com.illsang.user.service

import com.illsang.auth.domain.model.AuthenticationModel
import com.illsang.auth.domain.model.RefreshTokenData
import com.illsang.auth.enums.OAuthProvider
import com.illsang.auth.service.AuthenticationService
import com.illsang.auth.service.JwtTokenService
import com.illsang.user.domain.model.UserModel
import com.illsang.user.dto.request.CreateUserRequest
import com.illsang.user.dto.request.OAuthLoginRequest
import com.illsang.user.dto.request.RefreshLoginRequest
import com.illsang.user.dto.response.LoginResponse
import com.illsang.user.enums.UserStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.random.Random

@Service
@Transactional(readOnly = true)
class AuthService(
    private val jwtTokenService: JwtTokenService,
    private val userService: UserService,
    private val authenticationService: AuthenticationService,
) {

    companion object {
        private const val MAX_ATTEMPTS = 100
    }

    @Transactional
    fun oauthLogin(request: OAuthLoginRequest): LoginResponse {
        val userInfo = this.authenticationService.validateOAuthToken(OAuthLoginRequest.toOAuthLogin(request))

        val email = userInfo["email"] as String

        if (email.isBlank()) {
            throw IllegalArgumentException("Email cannot be blank from OAuth provider")
        }

        val provider = OAuthProvider.fromString(request.provider.name)

        val user = findOrCreateUser(email, provider)

        return LoginResponse.from(this.authenticationService.generateAndStoreTokens(user.id!!, user.roles))
    }

    fun refreshLogin(request: RefreshLoginRequest): LoginResponse {
        val userId = jwtTokenService.getUserIdFromToken(request.accessToken)

        // Validate user exists and get roles
        val user = this.userService.getUser(userId)

        val token = this.authenticationService.createByRefreshToken(
            userId,
            RefreshTokenData(accessToken = request.accessToken, refreshToken = request.refreshToken),
            user.roles,
        )

        return LoginResponse.from(token)
    }

    fun logout(authenticationModel: AuthenticationModel) {
        if (!authenticationModel.isAuthenticated()) {
            throw IllegalArgumentException("User is not authenticated")
        }

        authenticationService.deleteToken(authenticationModel.userId)
    }

    @Transactional
    fun withdraw(authenticationModel: AuthenticationModel) {
        this.logout(authenticationModel)

        this.userService.delete(authenticationModel.userId)
    }

    private fun findOrCreateUser(email: String, provider: OAuthProvider): UserModel {

        return userService.getUserByEmail(email) ?: run {
            val uniqueNickname = this.generateUniqueNickname()
            val userRequest = CreateUserRequest(
                email = email,
                channel = provider,
                nickname = uniqueNickname,
                status = UserStatus.ACTIVE,
                roles = listOf("USER"),
            )

            userService.createUser(userRequest)
        }
    }

    private fun generateUniqueNickname(): String {
        var attempts = 0

        while (attempts < MAX_ATTEMPTS) {
            val randomNumber = generateRandomNumber()
            val nickname = "일상${String.format("%08d", randomNumber)}"

            if (this.userService.getUserByNickname(nickname) == null) {
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
