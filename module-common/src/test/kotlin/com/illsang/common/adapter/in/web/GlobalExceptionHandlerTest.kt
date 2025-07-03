package com.illsang.common.adapter.`in`.web

import org.junit.jupiter.api.Test
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.web.servlet.resource.NoResourceFoundException
import kotlin.test.assertEquals

class GlobalExceptionHandlerTest {

    private val globalExceptionHandler = GlobalExceptionHandler()

    @Test
    fun `should return 404 when IllegalArgumentException contains not found message`() {
        // Given
        val exception = IllegalArgumentException("User not found with id: 123")

        // When
        val response = globalExceptionHandler.handleIllegalArgumentException(exception)

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
        assertEquals(404, response.body?.status)
        assertEquals("Not Found", response.body?.error)
        assertEquals("User not found with id: 123", response.body?.message)
    }

    @Test
    fun `should return 404 when IllegalArgumentException contains does not exist message`() {
        // Given
        val exception = IllegalArgumentException("User does not exist")

        // When
        val response = globalExceptionHandler.handleIllegalArgumentException(exception)

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
        assertEquals(404, response.body?.status)
        assertEquals("Not Found", response.body?.error)
        assertEquals("User does not exist", response.body?.message)
    }

    @Test
    fun `should return 400 when IllegalArgumentException contains already exists message`() {
        // Given
        val exception = IllegalArgumentException("User with email test@example.com already exists")

        // When
        val response = globalExceptionHandler.handleIllegalArgumentException(exception)

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
        assertEquals(400, response.body?.status)
        assertEquals("Bad Request", response.body?.error)
        assertEquals("User with email test@example.com already exists", response.body?.message)
    }

    @Test
    fun `should return 400 for general IllegalArgumentException`() {
        // Given
        val exception = IllegalArgumentException("Invalid input parameter")

        // When
        val response = globalExceptionHandler.handleIllegalArgumentException(exception)

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
        assertEquals(400, response.body?.status)
        assertEquals("Bad Request", response.body?.error)
        assertEquals("Invalid input parameter", response.body?.message)
    }

    @Test
    fun `should return 401 for BadCredentialsException`() {
        // Given
        val exception = BadCredentialsException("Invalid credentials")

        // When
        val response = globalExceptionHandler.handleBadCredentialsException(exception)

        // Then
        assertEquals(HttpStatus.UNAUTHORIZED, response.statusCode)
        assertEquals(401, response.body?.status)
        assertEquals("Unauthorized", response.body?.error)
        assertEquals("Invalid credentials", response.body?.message)
    }

    @Test
    fun `should return 404 for NoResourceFoundException`() {
        // Given
        val exception = NoResourceFoundException(HttpMethod.GET, "/api/nonexistent")

        // When
        val response = globalExceptionHandler.handleNoResourceFoundException(exception)

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
        assertEquals(404, response.body?.status)
        assertEquals("Not Found", response.body?.error)
        assertEquals("The requested resource was not found", response.body?.message)
    }

    @Test
    fun `should return 500 for generic Exception`() {
        // Given
        val exception = RuntimeException("Unexpected error")

        // When
        val response = globalExceptionHandler.handleGenericException(exception)

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.statusCode)
        assertEquals(500, response.body?.status)
        assertEquals("Internal Server Error", response.body?.error)
        assertEquals("An unexpected error occurred", response.body?.message)
    }
}
