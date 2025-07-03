package com.illsang.moduleuser.application.service

import com.illsang.moduleuser.application.port.out.UserPersistencePort
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class NicknameGenerationServiceTest {

    @Mock
    private lateinit var userPersistencePort: UserPersistencePort

    private lateinit var nicknameGenerationService: NicknameGenerationService

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        nicknameGenerationService = NicknameGenerationService(userPersistencePort)
    }

    @Test
    fun `generateUniqueNickname should return nickname with correct format`() {
        // Given
        `when`(userPersistencePort.existsByNickname(anyString())).thenReturn(false)

        // When
        val nickname = nicknameGenerationService.generateUniqueNickname()

        // Then
        assertTrue(nickname.startsWith("일상"))
        assertEquals(10, nickname.length) // "일상" (2 characters) + 8 digits = 10 characters total
        val numberPart = nickname.substring(2)
        assertTrue(numberPart.matches(Regex("\\d{8}"))) // Should be exactly 8 digits
    }

    @Test
    fun `generateUniqueNickname should generate unique nickname when first attempt exists`() {
        // Given
        var callCount = 0
        `when`(userPersistencePort.existsByNickname(anyString())).thenAnswer {
            callCount++
            callCount == 1 // Return true for first call, false for subsequent calls
        }

        // When
        val nickname = nicknameGenerationService.generateUniqueNickname()

        // Then
        assertTrue(nickname.startsWith("일상"))
        assertEquals(10, nickname.length)
        verify(userPersistencePort, atLeast(2)).existsByNickname(anyString())
    }

    @Test
    fun `generateUniqueNickname should use correct prefix`() {
        // Given
        `when`(userPersistencePort.existsByNickname(anyString())).thenReturn(false)

        // When
        val nickname = nicknameGenerationService.generateUniqueNickname()

        // Then
        assertTrue(nickname.startsWith("일상"))
        assertEquals(10, nickname.length)
    }

    @Test
    fun `generateUniqueNickname should throw exception when max attempts reached`() {
        // Given
        `when`(userPersistencePort.existsByNickname(anyString())).thenReturn(true)

        // When & Then
        assertThrows(IllegalStateException::class.java) {
            nicknameGenerationService.generateUniqueNickname()
        }
    }
}
