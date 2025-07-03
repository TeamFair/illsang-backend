package com.illsang.common.domain.model

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

class AuthenticationModelTest {

    @Test
    @DisplayName("인증된 사용자 생성 성공")
    fun `인증된 사용자를 생성한다`() {
        // Given
        val userId = "user123"
        val userType = UserType.CUSTOMER

        // When
        val authModel = AuthenticationModel.authenticated(userId, userType)

        // Then
        assertEquals(userId, authModel.userId)
        assertEquals(userType, authModel.userType)
        assertNull(authModel.group)
        assertTrue(authModel.isAuthenticated())
        assertFalse(authModel.hasGroup())
    }

    @Test
    @DisplayName("그룹이 있는 인증된 사용자 생성 성공")
    fun `그룹이 있는 인증된 사용자를 생성한다`() {
        // Given
        val userId = "boss123"
        val userType = UserType.BOSS
        val group = "MANAGEMENT"

        // When
        val authModel = AuthenticationModel.authenticated(userId, userType, group)

        // Then
        assertEquals(userId, authModel.userId)
        assertEquals(userType, authModel.userType)
        assertEquals(group, authModel.group)
        assertTrue(authModel.isAuthenticated())
        assertTrue(authModel.hasGroup())
    }

    @Test
    @DisplayName("인증되지 않은 사용자 생성")
    fun `인증되지 않은 사용자를 생성한다`() {
        // When
        val authModel = AuthenticationModel.unauthenticated()

        // Then
        assertNull(authModel.userId)
        assertEquals(UserType.UNKNOWN, authModel.userType)
        assertNull(authModel.group)
        assertFalse(authModel.isAuthenticated())
        assertFalse(authModel.hasGroup())
    }

    @Test
    @DisplayName("CUSTOMER 타입 사용자 인증 확인")
    fun `CUSTOMER 타입 사용자가 인증된다`() {
        // Given
        val userId = "customer123"
        val userType = UserType.CUSTOMER

        // When
        val authModel = AuthenticationModel.authenticated(userId, userType)

        // Then
        assertTrue(authModel.isAuthenticated())
        assertEquals(UserType.CUSTOMER, authModel.userType)
    }

    @Test
    @DisplayName("ADMIN 타입 사용자 인증 확인")
    fun `ADMIN 타입 사용자가 인증된다`() {
        // Given
        val userId = "admin123"
        val userType = UserType.ADMIN

        // When
        val authModel = AuthenticationModel.authenticated(userId, userType)

        // Then
        assertTrue(authModel.isAuthenticated())
        assertEquals(UserType.ADMIN, authModel.userType)
    }

    @Test
    @DisplayName("BOSS 타입 사용자 인증 확인")
    fun `BOSS 타입 사용자가 인증된다`() {
        // Given
        val userId = "boss123"
        val userType = UserType.BOSS
        val group = "MANAGEMENT"

        // When
        val authModel = AuthenticationModel.authenticated(userId, userType, group)

        // Then
        assertTrue(authModel.isAuthenticated())
        assertEquals(UserType.BOSS, authModel.userType)
        assertTrue(authModel.hasGroup())
        assertEquals(group, authModel.group)
    }

    @Test
    @DisplayName("UNKNOWN 타입 사용자는 인증되지 않음")
    fun `UNKNOWN 타입 사용자는 인증되지 않는다`() {
        // Given
        val userId = "user123"
        val userType = UserType.UNKNOWN

        // When
        val authModel = AuthenticationModel(userType, userId)

        // Then
        assertFalse(authModel.isAuthenticated())
        assertEquals(UserType.UNKNOWN, authModel.userType)
    }

    @Test
    @DisplayName("userId가 null인 경우 인증되지 않음")
    fun `userId가 null인 경우 인증되지 않는다`() {
        // Given
        val userType = UserType.CUSTOMER

        // When
        val authModel = AuthenticationModel(userType, null)

        // Then
        assertFalse(authModel.isAuthenticated())
        assertNull(authModel.userId)
    }

    @Test
    @DisplayName("그룹이 null인 경우 hasGroup은 false")
    fun `그룹이 null인 경우 hasGroup은 false를 반환한다`() {
        // Given
        val userId = "user123"
        val userType = UserType.CUSTOMER

        // When
        val authModel = AuthenticationModel.authenticated(userId, userType)

        // Then
        assertFalse(authModel.hasGroup())
        assertNull(authModel.group)
    }

    @Test
    @DisplayName("그룹이 빈 문자열인 경우에도 hasGroup은 false")
    fun `그룹이 빈 문자열인 경우에도 hasGroup은 false를 반환한다`() {
        // Given
        val userId = "user123"
        val userType = UserType.CUSTOMER
        val group = ""

        // When
        val authModel = AuthenticationModel.authenticated(userId, userType, group)

        // Then
        assertFalse(authModel.hasGroup())
        assertEquals("", authModel.group)
    }

    @Test
    @DisplayName("다양한 그룹명으로 사용자 생성")
    fun `다양한 그룹명으로 사용자를 생성한다`() {
        // Given
        val testCases = listOf(
            Triple("user1", UserType.BOSS, "MANAGEMENT"),
            Triple("user2", UserType.ADMIN, "SUPPORT"),
            Triple("user3", UserType.CUSTOMER, "VIP")
        )

        testCases.forEach { (userId, userType, group) ->
            // When
            val authModel = AuthenticationModel.authenticated(userId, userType, group)

            // Then
            assertTrue(authModel.isAuthenticated())
            assertTrue(authModel.hasGroup())
            assertEquals(userId, authModel.userId)
            assertEquals(userType, authModel.userType)
            assertEquals(group, authModel.group)
        }
    }

    @Test
    @DisplayName("동일한 AuthenticationModel 비교")
    fun `동일한 AuthenticationModel을 비교한다`() {
        // Given
        val userId = "user123"
        val userType = UserType.CUSTOMER
        val group = "VIP"

        // When
        val authModel1 = AuthenticationModel.authenticated(userId, userType, group)
        val authModel2 = AuthenticationModel.authenticated(userId, userType, group)

        // Then
        assertEquals(authModel1, authModel2)
        assertEquals(authModel1.hashCode(), authModel2.hashCode())
    }

    @Test
    @DisplayName("다른 AuthenticationModel 비교")
    fun `다른 AuthenticationModel을 비교한다`() {
        // Given
        val authModel1 = AuthenticationModel.authenticated("user1", UserType.CUSTOMER)
        val authModel2 = AuthenticationModel.authenticated("user2", UserType.ADMIN)

        // Then
        assertFalse(authModel1 == authModel2)
    }

    @Test
    @DisplayName("인증된 사용자와 인증되지 않은 사용자 비교")
    fun `인증된 사용자와 인증되지 않은 사용자를 비교한다`() {
        // Given
        val authenticatedUser = AuthenticationModel.authenticated("user123", UserType.CUSTOMER)
        val unauthenticatedUser = AuthenticationModel.unauthenticated()

        // Then
        assertTrue(authenticatedUser.isAuthenticated())
        assertFalse(unauthenticatedUser.isAuthenticated())
        assertFalse(authenticatedUser == unauthenticatedUser)
    }
}
