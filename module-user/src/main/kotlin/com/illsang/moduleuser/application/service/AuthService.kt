package com.illsang.moduleuser.application.service

import com.illsang.common.application.port.out.TokenPersistencePort
import com.illsang.common.domain.model.AuthenticationModel
import com.illsang.moduleuser.adapter.`in`.web.model.request.OAuthLoginRequest
import com.illsang.moduleuser.adapter.`in`.web.model.request.RefreshLoginRequest
import com.illsang.moduleuser.adapter.`in`.web.model.response.LoginResponse
import com.illsang.moduleuser.application.command.CreateUserCommand
import com.illsang.moduleuser.domain.enums.OAuthProvider
import com.illsang.moduleuser.domain.enums.OSType
import com.illsang.moduleuser.domain.model.UserModel
import com.illsang.moduleuser.domain.model.UserStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class AuthService(
    private val googleTokenValidationService: GoogleTokenValidationService,
    private val appleTokenValidationService: AppleTokenValidationService,
    private val jwtTokenService: JwtTokenService,
    private val userService: UserService,
    private val tokenPersistencePort: TokenPersistencePort,
    private val nicknameGenerationService: NicknameGenerationService
) {

    @Transactional
    fun oauthLogin(request: OAuthLoginRequest): LoginResponse {
        // Validate the OAuth token based on provider
        val userInfo = validateOAuthToken(request)

        val email = userInfo["email"] as String

        if (email.isBlank()) {
            throw IllegalArgumentException("Email cannot be blank from OAuth provider")
        }

        val provider = OAuthProvider.fromString(request.provider)

        val user = findOrCreateUser(email, provider)

        // Generate and store tokens
        return generateAndStoreTokens(user)
    }

    fun refreshLogin(request: RefreshLoginRequest): LoginResponse {
        // Extract user ID from access token
        val userId = jwtTokenService.getUserIdFromToken(request.accessToken)

        // Validate user exists
        val user = userService.getUser(userId)

        // Get stored tokens from Redis
        val storedRefreshTokenData = tokenPersistencePort.getRefreshTokenData(userId.toString())
            ?: throw IllegalArgumentException("No refresh token found for user")

        // Compare tokens with stored ones
        if (storedRefreshTokenData.accessToken != request.accessToken ||
            storedRefreshTokenData.refreshToken != request.refreshToken) {
            throw IllegalArgumentException("Invalid tokens")
        }

        // Generate new tokens
        return generateAndStoreTokens(user)
    }

    @Transactional
    fun logout(authenticationModel: AuthenticationModel) {
        if (!authenticationModel.isAuthenticated()) {
            throw IllegalArgumentException("User is not authenticated")
        }

        val userId = authenticationModel.userId!!

        // Delete both access token and refresh token from Redis
        tokenPersistencePort.deleteTokens(userId)
    }

    private fun validateOAuthToken(request: OAuthLoginRequest): Map<String, String> {
        val osType = OSType.fromString(request.osType)

        val provider = OAuthProvider.fromString(request.provider)

        return when (provider) {
            OAuthProvider.GOOGLE -> {
                val payload = googleTokenValidationService.validateIdToken(request.idToken, osType)

                mapOf(
                    "email" to payload.email,
                )
            }
            OAuthProvider.APPLE -> {
                val claims = appleTokenValidationService.validateIdToken(request.idToken, osType)

                mapOf(
                    "email" to (claims["email"] as? String ?: ""),
                )
            }
        }
    }

    private fun findOrCreateUser(email: String, provider: OAuthProvider): UserModel {
        return try {
            userService.getUserByEmail(email)
        } catch (e: IllegalArgumentException) {
            // User doesn't exist, create new one
            val uniqueNickname = nicknameGenerationService.generateUniqueNickname()
            val createUserCommand = CreateUserCommand(
                email = email,
                channel = provider.name,
                nickname = uniqueNickname,
                status = UserStatus.ACTIVE
            )
            userService.createUser(createUserCommand)
        }
    }

    private fun generateAndStoreTokens(user: UserModel): LoginResponse {
        // Generate tokens
        val accessToken = jwtTokenService.generateAccessToken(user.id!!)
        val refreshToken = jwtTokenService.generateRefreshToken(user.id)

        // Store tokens in Redis
        tokenPersistencePort.storeAccessToken(
            user.id.toString(),
            accessToken,
            jwtTokenService.getAccessTokenExpirationMinutes()
        )

        tokenPersistencePort.storeRefreshToken(
            user.id.toString(),
            accessToken,
            refreshToken,
            jwtTokenService.getRefreshTokenExpirationMinutes()
        )

        return LoginResponse(
            accessToken = accessToken,
            refreshToken = refreshToken
        )
    }
}
