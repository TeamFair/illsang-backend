package com.teamfair.moduleuser.adapter.`in`.web

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.teamfair.moduleuser.application.command.CreateUserCommand
import com.teamfair.moduleuser.application.command.UpdateUserCommand
import com.teamfair.moduleuser.application.service.UserService
import com.teamfair.moduleuser.domain.model.UserModel
import com.teamfair.moduleuser.domain.model.UserStatus
import org.junit.jupiter.api.DisplayName
import org.mockito.BDDMockito.given
import org.mockito.Mockito.doNothing
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.*
import java.time.LocalDateTime
import java.util.*
import kotlin.test.Test

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(UserController::class)
class UserControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockitoBean
    private lateinit var userService: UserService

    private val objectMapper = jacksonObjectMapper()

    @Test
    @DisplayName("POST /api/v1/users - 사용자 생성 성공")
    fun `사용자를 생성하면 201 CREATED와 생성된 사용자를 반환한다`() {
        // Given
        val command = CreateUserCommand(
            email = "test@example.com",
            channel = "EMAIL",
            nickname = "테스트유저",
            status = UserStatus.ACTIVE
        )
        val createdUser = UserModel(
            id = 1L,
            email = command.email,
            channel = command.channel,
            nickname = command.nickname,
            status = command.status,
            statusUpdatedAt = LocalDateTime.now(),
            profileImageId = UUID.randomUUID()
        )
        given(userService.createUser(command)).willReturn(createdUser)

        // When & Then
        mockMvc.post("/api/v1/users") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(command)
        }.andExpect {
            status { isCreated() }
            content { contentType(MediaType.APPLICATION_JSON) }
        }
    }

    @Test
    @DisplayName("GET /api/v1/users/{id} - 사용자 단건 조회 성공")
    fun `사용자를 ID로 조회하면 200 OK와 상세 정보를 반환한다`() {
        // Given
        val user = UserModel(
            id = 1L,
            email = "test@example.com",
            channel = "EMAIL",
            nickname = "테스트유저",
            status = UserStatus.ACTIVE,
            statusUpdatedAt = LocalDateTime.now(),
            profileImageId = UUID.randomUUID()
        )
        given(userService.getUser(1L)).willReturn(user)

        // When & Then
        mockMvc.get("/api/v1/users/1")
            .andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
            }
    }

    @Test
    @DisplayName("GET /api/v1/users/email/{email} - 이메일로 사용자 조회 성공")
    fun `이메일로 사용자를 조회하면 200 OK와 상세 정보를 반환한다`() {
        // Given
        val user = UserModel(
            id = 1L,
            email = "test@example.com",
            channel = "EMAIL",
            nickname = "테스트유저",
            status = UserStatus.ACTIVE,
            statusUpdatedAt = LocalDateTime.now(),
            profileImageId = UUID.randomUUID()
        )
        given(userService.getUserByEmail("test@example.com")).willReturn(user)

        // When & Then
        mockMvc.get("/api/v1/users/email/test@example.com")
            .andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
            }
    }

    @Test
    @DisplayName("GET /api/v1/users - 사용자 전체 목록 조회 성공")
    fun `사용자 전체 목록을 조회하면 200 OK와 리스트를 반환한다`() {
        // Given
        val users = listOf(
            UserModel(
                id = 1L,
                email = "user1@example.com",
                channel = "EMAIL",
                nickname = "유저1",
                status = UserStatus.ACTIVE,
                statusUpdatedAt = LocalDateTime.now()
            ),
            UserModel(
                id = 2L,
                email = "user2@example.com",
                channel = "GOOGLE",
                nickname = "유저2",
                status = UserStatus.INACTIVE,
                statusUpdatedAt = LocalDateTime.now()
            )
        )
        given(userService.getAllUsers()).willReturn(users)

        // When & Then
        mockMvc.get("/api/v1/users")
            .andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
                jsonPath("$.size()") { value(2) }
            }
    }

    @Test
    @DisplayName("PUT /api/v1/users/{id} - 사용자 수정 성공")
    fun `사용자를 수정하면 200 OK와 수정된 사용자를 반환한다`() {
        // Given
        val updateCommand = UpdateUserCommand(
            id = 1L,
            nickname = "수정된닉네임",
            status = UserStatus.ACTIVE
        )
        val updatedUser = UserModel(
            id = 1L,
            email = "test@example.com",
            channel = "EMAIL",
            nickname = updateCommand.nickname!!,
            status = updateCommand.status!!,
            statusUpdatedAt = LocalDateTime.now()
        )
        given(userService.updateUser(updateCommand)).willReturn(updatedUser)

        // When & Then
        mockMvc.put("/api/v1/users/1") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(updateCommand.copy(id = 1L))
        }.andExpect {
            status { isOk() }
            content { contentType(MediaType.APPLICATION_JSON) }
        }
    }

    @Test
    @DisplayName("DELETE /api/v1/users/{id} - 사용자 삭제 성공")
    fun `사용자를 삭제하면 200 OK와 성공 메시지를 반환한다`() {
        // Given
        doNothing().`when`(userService).deleteUser(1L)

        // When & Then
        mockMvc.delete("/api/v1/users/1")
            .andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
            }
    }
}