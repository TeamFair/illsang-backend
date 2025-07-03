package com.illsang.common.domain.model

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class TokenModelTest {

    @Test
    @DisplayName("유효한 토큰으로 TokenModel 생성 성공")
    fun `유효한 토큰으로 TokenModel을 생성한다`() {
        // Given
        val accessToken = "valid-access-token"
        val refreshToken = "valid-refresh-token"

        // When
        val tokenModel = TokenModel.create(accessToken, refreshToken)

        // Then
        assertEquals(accessToken, tokenModel.accessToken)
        assertEquals(refreshToken, tokenModel.refreshToken)
    }

    @Test
    @DisplayName("빈 액세스 토큰으로 TokenModel 생성 실패")
    fun `빈 액세스 토큰으로 TokenModel 생성 시 예외가 발생한다`() {
        // Given
        val accessToken = ""
        val refreshToken = "valid-refresh-token"

        // When & Then
        val exception = assertThrows<IllegalArgumentException> {
            TokenModel.create(accessToken, refreshToken)
        }
        assertEquals("Access token cannot be blank", exception.message)
    }

    @Test
    @DisplayName("빈 리프레시 토큰으로 TokenModel 생성 실패")
    fun `빈 리프레시 토큰으로 TokenModel 생성 시 예외가 발생한다`() {
        // Given
        val accessToken = "valid-access-token"
        val refreshToken = ""

        // When & Then
        val exception = assertThrows<IllegalArgumentException> {
            TokenModel.create(accessToken, refreshToken)
        }
        assertEquals("Refresh token cannot be blank", exception.message)
    }

    @Test
    @DisplayName("공백 액세스 토큰으로 TokenModel 생성 실패")
    fun `공백 액세스 토큰으로 TokenModel 생성 시 예외가 발생한다`() {
        // Given
        val accessToken = "   "
        val refreshToken = "valid-refresh-token"

        // When & Then
        val exception = assertThrows<IllegalArgumentException> {
            TokenModel.create(accessToken, refreshToken)
        }
        assertEquals("Access token cannot be blank", exception.message)
    }

    @Test
    @DisplayName("유효한 토큰 맵으로 TokenModel 생성 성공")
    fun `유효한 토큰 맵으로 TokenModel을 생성한다`() {
        // Given
        val tokenMap = mapOf(
            "accessToken" to "valid-access-token",
            "refreshToken" to "valid-refresh-token"
        )

        // When
        val tokenModel = TokenModel.fromTokenMap(tokenMap)

        // Then
        assertEquals("valid-access-token", tokenModel.accessToken)
        assertEquals("valid-refresh-token", tokenModel.refreshToken)
    }

    @Test
    @DisplayName("액세스 토큰이 없는 맵으로 TokenModel 생성 실패")
    fun `액세스 토큰이 없는 맵으로 TokenModel 생성 시 예외가 발생한다`() {
        // Given
        val tokenMap = mapOf(
            "refreshToken" to "valid-refresh-token"
        )

        // When & Then
        val exception = assertThrows<IllegalArgumentException> {
            TokenModel.fromTokenMap(tokenMap)
        }
        assertEquals("Access token is required", exception.message)
    }

    @Test
    @DisplayName("리프레시 토큰이 없는 맵으로 TokenModel 생성 실패")
    fun `리프레시 토큰이 없는 맵으로 TokenModel 생성 시 예외가 발생한다`() {
        // Given
        val tokenMap = mapOf(
            "accessToken" to "valid-access-token"
        )

        // When & Then
        val exception = assertThrows<IllegalArgumentException> {
            TokenModel.fromTokenMap(tokenMap)
        }
        assertEquals("Refresh token is required", exception.message)
    }

    @Test
    @DisplayName("올바른 액세스 토큰 검증 성공")
    fun `올바른 액세스 토큰으로 검증이 성공한다`() {
        // Given
        val tokenModel = TokenModel("access-token", "refresh-token")
        val providedAccessToken = "access-token"

        // When
        val result = tokenModel.isAccessTokenValid(providedAccessToken)

        // Then
        assertTrue(result)
    }

    @Test
    @DisplayName("잘못된 액세스 토큰 검증 실패")
    fun `잘못된 액세스 토큰으로 검증이 실패한다`() {
        // Given
        val tokenModel = TokenModel("access-token", "refresh-token")
        val providedAccessToken = "wrong-access-token"

        // When
        val result = tokenModel.isAccessTokenValid(providedAccessToken)

        // Then
        assertFalse(result)
    }

    @Test
    @DisplayName("올바른 리프레시 토큰 검증 성공")
    fun `올바른 리프레시 토큰으로 검증이 성공한다`() {
        // Given
        val tokenModel = TokenModel("access-token", "refresh-token")
        val providedRefreshToken = "refresh-token"

        // When
        val result = tokenModel.isRefreshTokenValid(providedRefreshToken)

        // Then
        assertTrue(result)
    }

    @Test
    @DisplayName("잘못된 리프레시 토큰 검증 실패")
    fun `잘못된 리프레시 토큰으로 검증이 실패한다`() {
        // Given
        val tokenModel = TokenModel("access-token", "refresh-token")
        val providedRefreshToken = "wrong-refresh-token"

        // When
        val result = tokenModel.isRefreshTokenValid(providedRefreshToken)

        // Then
        assertFalse(result)
    }

    @Test
    @DisplayName("올바른 두 토큰으로 전체 검증 성공")
    fun `올바른 액세스 토큰과 리프레시 토큰으로 전체 검증이 성공한다`() {
        // Given
        val tokenModel = TokenModel("access-token", "refresh-token")
        val providedAccessToken = "access-token"
        val providedRefreshToken = "refresh-token"

        // When
        val result = tokenModel.isValid(providedRefreshToken, providedAccessToken)

        // Then
        assertTrue(result)
    }

    @Test
    @DisplayName("잘못된 액세스 토큰으로 전체 검증 실패")
    fun `잘못된 액세스 토큰으로 전체 검증이 실패한다`() {
        // Given
        val tokenModel = TokenModel("access-token", "refresh-token")
        val providedAccessToken = "wrong-access-token"
        val providedRefreshToken = "refresh-token"

        // When
        val result = tokenModel.isValid(providedRefreshToken, providedAccessToken)

        // Then
        assertFalse(result)
    }

    @Test
    @DisplayName("잘못된 리프레시 토큰으로 전체 검증 실패")
    fun `잘못된 리프레시 토큰으로 전체 검증이 실패한다`() {
        // Given
        val tokenModel = TokenModel("access-token", "refresh-token")
        val providedAccessToken = "access-token"
        val providedRefreshToken = "wrong-refresh-token"

        // When
        val result = tokenModel.isValid(providedRefreshToken, providedAccessToken)

        // Then
        assertFalse(result)
    }

    @Test
    @DisplayName("두 토큰 모두 잘못된 경우 전체 검증 실패")
    fun `두 토큰 모두 잘못된 경우 전체 검증이 실패한다`() {
        // Given
        val tokenModel = TokenModel("access-token", "refresh-token")
        val providedAccessToken = "wrong-access-token"
        val providedRefreshToken = "wrong-refresh-token"

        // When
        val result = tokenModel.isValid(providedRefreshToken, providedAccessToken)

        // Then
        assertFalse(result)
    }
}
