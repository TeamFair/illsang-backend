package com.illsang.common.adapter.`in`.web

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.resource.NoResourceFoundException

@ControllerAdvice
class GlobalExceptionHandler {

    private val logger = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(ex: IllegalArgumentException): ResponseEntity<ErrorResponse> {
        logger.error("IllegalArgumentException occurred", ex)
        ex.printStackTrace()
        val errorResponse = ErrorResponse(
            status = HttpStatus.BAD_REQUEST.value(),
            error = "Bad Request",
            message = ex.message ?: "Invalid request parameters"
        )

        // Check if the exception message indicates a "not found" scenario
        val message = ex.message?.lowercase() ?: ""
        if (message.contains("not found") || message.contains("does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                errorResponse.copy(
                    status = HttpStatus.NOT_FOUND.value(),
                    error = "Not Found"
                )
            )
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse)
    }

    @ExceptionHandler(BadCredentialsException::class)
    fun handleBadCredentialsException(ex: BadCredentialsException): ResponseEntity<ErrorResponse> {
        logger.error("BadCredentialsException occurred", ex)
        ex.printStackTrace()
        val errorResponse = ErrorResponse(
            status = HttpStatus.UNAUTHORIZED.value(),
            error = "Unauthorized",
            message = ex.message ?: "Authentication failed"
        )
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse)
    }

    @ExceptionHandler(NoResourceFoundException::class)
    fun handleNoResourceFoundException(ex: NoResourceFoundException): ResponseEntity<ErrorResponse> {
        logger.error("NoResourceFoundException occurred", ex)
        ex.printStackTrace()
        val errorResponse = ErrorResponse(
            status = HttpStatus.NOT_FOUND.value(),
            error = "Not Found",
            message = "The requested resource was not found"
        )
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse)
    }

    @ExceptionHandler(Exception::class)
    fun handleGenericException(ex: Exception): ResponseEntity<ErrorResponse> {
        logger.error("Unexpected exception occurred", ex)
        ex.printStackTrace()
        val errorResponse = ErrorResponse(
            status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
            error = "Internal Server Error",
            message = "An unexpected error occurred"
        )
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse)
    }

    data class ErrorResponse(
        val status: Int,
        val error: String,
        val message: String
    )
}
