package com.illsang.moduleuser.application.service

import com.illsang.common.application.port.out.TokenPersistencePort
import com.illsang.common.domain.model.AuthenticationModel
import com.illsang.common.domain.model.RefreshTokenData
import com.illsang.common.domain.model.UserType
import com.illsang.moduleuser.adapter.`in`.web.model.request.RefreshLoginRequest
import com.illsang.moduleuser.domain.enums.OAuthProvider
import com.illsang.moduleuser.domain.model.UserModel
import com.illsang.moduleuser.domain.model.UserStatus
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import java.time.LocalDateTime

class AuthServiceTest {

    @Mock
    private lateinit var googleTokenValidationService: GoogleTokenValidationService

    @Mock
    private lateinit var appleTokenValidationService: AppleTokenValidationService

    @Mock
    private lateinit var jwtTokenService: JwtTokenService

    @Mock
    private lateinit var userService: UserService

    @Mock
    private lateinit var tokenPersistencePort: TokenPersistencePort

    @Mock
    private lateinit var nicknameGenerationService: NicknameGenerationService

    private lateinit var authService: AuthService

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        authService = AuthService(
            googleTokenValidationService,
            appleTokenValidationService,
            jwtTokenService,
            userService,
            tokenPersistencePort,
            nicknameGenerationService
        )
    }

    @Test
    fun `refreshLogin should return new tokens when valid tokens are provided`() {
        // Given
        val userId = 1L
        val accessToken = "valid-access-token"
        val refreshToken = "valid-refresh-token"
        val newAccessToken = "new-access-token"
        val newRefreshToken = "new-refresh-token"

        val request = RefreshLoginRequest(accessToken, refreshToken)
        val user = UserModel(
            id = userId,
            email = "test@example.com",
            channel = OAuthProvider.GOOGLE,
            nickname = "testuser",
            status = UserStatus.ACTIVE,
            statusUpdatedAt = LocalDateTime.now()
        )
        val storedTokenData = RefreshTokenData(accessToken, refreshToken)

        `when`(jwtTokenService.getUserIdFromToken(accessToken)).thenReturn(userId)
        `when`(userService.getUser(userId)).thenReturn(user)
        `when`(tokenPersistencePort.getRefreshTokenData(userId.toString())).thenReturn(storedTokenData)
        `when`(jwtTokenService.generateAccessToken(userId)).thenReturn(newAccessToken)
        `when`(jwtTokenService.generateRefreshToken(userId)).thenReturn(newRefreshToken)
        `when`(jwtTokenService.getAccessTokenExpirationMinutes()).thenReturn(60L)
        `when`(jwtTokenService.getRefreshTokenExpirationMinutes()).thenReturn(1440L)

        // When
        val result = authService.refreshLogin(request)

        // Then
        assertEquals(newAccessToken, result.accessToken)
        assertEquals(newRefreshToken, result.refreshToken)

        verify(jwtTokenService).getUserIdFromToken(accessToken)
        verify(userService).getUser(userId)
        verify(tokenPersistencePort).getRefreshTokenData(userId.toString())
        verify(tokenPersistencePort).storeAccessToken(userId.toString(), newAccessToken, 60L)
        verify(tokenPersistencePort).storeRefreshToken(userId.toString(), newAccessToken, newRefreshToken, 1440L)
    }

    @Test
    fun `refreshLogin should throw exception when no refresh token found in Redis`() {
        // Given
        val userId = 1L
        val accessToken = "valid-access-token"
        val refreshToken = "valid-refresh-token"
        val request = RefreshLoginRequest(accessToken, refreshToken)
        val user = UserModel(
            id = userId,
            email = "test@example.com",
            channel = OAuthProvider.GOOGLE,
            nickname = "testuser",
            status = UserStatus.ACTIVE,
            statusUpdatedAt = LocalDateTime.now()
        )

        `when`(jwtTokenService.getUserIdFromToken(accessToken)).thenReturn(userId)
        `when`(userService.getUser(userId)).thenReturn(user)
        `when`(tokenPersistencePort.getRefreshTokenData(userId.toString())).thenReturn(null)

        // When & Then
        val exception = assertThrows(IllegalArgumentException::class.java) {
            authService.refreshLogin(request)
        }
        assertEquals("No refresh token found for user", exception.message)
    }

    @Test
    fun `refreshLogin should throw exception when tokens do not match`() {
        // Given
        val userId = 1L
        val accessToken = "valid-access-token"
        val refreshToken = "valid-refresh-token"
        val request = RefreshLoginRequest(accessToken, refreshToken)
        val user = UserModel(
            id = userId,
            email = "test@example.com",
            channel = OAuthProvider.GOOGLE,
            nickname = "testuser",
            status = UserStatus.ACTIVE,
            statusUpdatedAt = LocalDateTime.now()
        )
        val storedTokenData = RefreshTokenData("different-access-token", "different-refresh-token")

        `when`(jwtTokenService.getUserIdFromToken(accessToken)).thenReturn(userId)
        `when`(userService.getUser(userId)).thenReturn(user)
        `when`(tokenPersistencePort.getRefreshTokenData(userId.toString())).thenReturn(storedTokenData)

        // When & Then
        val exception = assertThrows(IllegalArgumentException::class.java) {
            authService.refreshLogin(request)
        }
        assertEquals("Invalid tokens", exception.message)
    }

    @Test
    fun `logout should delete tokens when user is authenticated`() {
        // Given
        val userId = "123"
        val authenticationModel = AuthenticationModel.authenticated(userId, UserType.CUSTOMER)

        // When
        authService.logout(authenticationModel)

        // Then
        verify(tokenPersistencePort).deleteTokens(userId)
    }

    @Test
    fun `logout should throw exception when user is not authenticated`() {
        // Given
        val authenticationModel = AuthenticationModel.unauthenticated()

        // When & Then
        val exception = assertThrows(IllegalArgumentException::class.java) {
            authService.logout(authenticationModel)
        }
        assertEquals("User is not authenticated", exception.message)

        // Verify that deleteTokens is never called
        verify(tokenPersistencePort, never()).deleteTokens(anyString())
    }
}
