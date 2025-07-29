package com.illsang.common.application.service

import com.illsang.auth.service.AuthenticationService
import com.illsang.common.application.port.out.TokenPersistencePort
import com.illsang.auth.model.AuthenticationModel
import com.illsang.auth.enums.UserType
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@ExtendWith(MockitoExtension::class)
class AuthenticationServiceTest {

    @Mock
    private lateinit var tokenPersistencePort: TokenPersistencePort

    @InjectMocks
    private lateinit var authenticationService: AuthenticationService

    @Test
    @DisplayName("유효한 토큰으로 토큰 검증 성공")
    fun `유효한 토큰으로 토큰 검증이 성공한다`() {
        // Given
        val userId = "user123"
        val token = "valid-token"
        given(tokenPersistencePort.getAccessToken(userId)).willReturn(token)

        // When
        val result = authenticationService.isTokenValid(userId, token)

        // Then
        assertTrue(result)
    }

    @Test
    @DisplayName("무효한 토큰으로 토큰 검증 실패")
    fun `무효한 토큰으로 토큰 검증이 실패한다`() {
        // Given
        val userId = "user123"
        val storedToken = "stored-token"
        val providedToken = "invalid-token"
        given(tokenPersistencePort.getAccessToken(userId)).willReturn(storedToken)

        // When
        val result = authenticationService.isTokenValid(userId, providedToken)

        // Then
        assertFalse(result)
    }

    @Test
    @DisplayName("저장된 토큰이 없을 때 토큰 검증 실패")
    fun `저장된 토큰이 없을 때 토큰 검증이 실패한다`() {
        // Given
        val userId = "user123"
        val token = "any-token"
        given(tokenPersistencePort.getAccessToken(userId)).willReturn(null)

        // When
        val result = authenticationService.isTokenValid(userId, token)

        // Then
        assertFalse(result)
    }

    @Test
    @DisplayName("인증된 사용자와 유효한 토큰으로 인증 검증 성공")
    fun `인증된 사용자와 유효한 토큰으로 인증 검증이 성공한다`() {
        // Given
        val userId = "user123"
        val token = "Bearer valid-token"
        val authentication = AuthenticationModel.authenticated(userId, UserType.CUSTOMER)
        given(tokenPersistencePort.getAccessToken(userId)).willReturn(token)

        // When
        val result = authenticationService.validateAuthentication(authentication, token)

        // Then
        assertTrue(result)
    }

    @Test
    @DisplayName("인증되지 않은 사용자로 인증 검증 실패")
    fun `인증되지 않은 사용자로 인증 검증이 실패한다`() {
        // Given
        val token = "Bearer any-token"
        val authentication = AuthenticationModel.unauthenticated()

        // When
        val result = authenticationService.validateAuthentication(authentication, token)

        // Then
        assertFalse(result)
    }

    @Test
    @DisplayName("인증된 사용자이지만 무효한 토큰으로 인증 검증 실패")
    fun `인증된 사용자이지만 무효한 토큰으로 인증 검증이 실패한다`() {
        // Given
        val userId = "user123"
        val storedToken = "Bearer stored-token"
        val providedToken = "Bearer invalid-token"
        val authentication = AuthenticationModel.authenticated(userId, UserType.CUSTOMER)
        given(tokenPersistencePort.getAccessToken(userId)).willReturn(storedToken)

        // When
        val result = authenticationService.validateAuthentication(authentication, providedToken)

        // Then
        assertFalse(result)
    }

    @Test
    @DisplayName("다양한 사용자 타입으로 인증 검증")
    fun `다양한 사용자 타입으로 인증 검증이 가능하다`() {
        // Given
        val userId = "boss123"
        val token = "Bearer boss-token"
        val authentication = AuthenticationModel.authenticated(userId, UserType.BOSS, "MANAGEMENT")
        given(tokenPersistencePort.getAccessToken(userId)).willReturn(token)

        // When
        val result = authenticationService.validateAuthentication(authentication, token)

        // Then
        assertTrue(result)
    }
}
