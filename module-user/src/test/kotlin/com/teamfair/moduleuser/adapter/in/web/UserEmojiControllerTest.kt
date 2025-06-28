package com.teamfair.moduleuser.adapter.`in`.web

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.teamfair.moduleuser.application.command.CreateUserEmojiCommand
import com.teamfair.moduleuser.application.command.UpdateUserEmojiCommand
import com.teamfair.moduleuser.application.service.UserEmojiService
import com.teamfair.moduleuser.domain.model.UserEmojiModel
import org.junit.jupiter.api.DisplayName
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.*
import java.time.LocalDateTime
import kotlin.test.Test

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(UserEmojiController::class)
class UserEmojiControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockitoBean
    private lateinit var userEmojiService: UserEmojiService

    private val objectMapper = jacksonObjectMapper()

    @Test
    @DisplayName("POST /api/user-emojis - 사용자 이모지 생성 성공")
    fun `사용자 이모지를 생성하면 201 CREATED와 생성된 이모지를 반환한다`() {
        // Given
        val command = CreateUserEmojiCommand(
            userId = 1L,
            emojiId = 100L,
            isEquipped = false
        )
        val createdUserEmoji = UserEmojiModel(
            id = 1L,
            userId = command.userId,
            emojiId = command.emojiId,
            isEquipped = command.isEquipped
        )
        given(userEmojiService.createUserEmoji(command)).willReturn(createdUserEmoji)

        // When & Then
        mockMvc.post("/api/user-emojis") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(command)
        }.andExpect {
            status { isCreated() }
            content { contentType(MediaType.APPLICATION_JSON) }
            jsonPath("$.id") { value(1) }
            jsonPath("$.userId") { value(command.userId) }
            jsonPath("$.emojiId") { value(command.emojiId) }
            jsonPath("$.isEquipped") { value(command.isEquipped) }
        }
    }

    @Test
    @DisplayName("GET /api/user-emojis/{id} - 사용자 이모지 단건 조회 성공")
    fun `사용자 이모지를 ID로 조회하면 200 OK와 상세 정보를 반환한다`() {
        // Given
        val userEmoji = UserEmojiModel(
            id = 1L,
            userId = 1L,
            emojiId = 100L,
            isEquipped = false
        )
        given(userEmojiService.getUserEmojiById(1L)).willReturn(userEmoji)

        // When & Then
        mockMvc.get("/api/user-emojis/1")
            .andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
                jsonPath("$.id") { value(1) }
                jsonPath("$.userId") { value(1) }
                jsonPath("$.emojiId") { value(100) }
                jsonPath("$.isEquipped") { value(false) }
            }
    }

    @Test
    @DisplayName("GET /api/user-emojis/user/{userId} - 사용자별 이모지 목록 조회 성공")
    fun `특정 사용자의 이모지 목록을 조회하면 200 OK와 리스트를 반환한다`() {
        // Given
        val userEmojis = listOf(
            UserEmojiModel(
                id = 1L,
                userId = 1L,
                emojiId = 100L,
                isEquipped = false
            ),
            UserEmojiModel(
                id = 2L,
                userId = 1L,
                emojiId = 101L,
                isEquipped = true
            )
        )
        given(userEmojiService.getUserEmojisByUserId(1L)).willReturn(userEmojis)

        // When & Then
        mockMvc.get("/api/user-emojis/user/1")
            .andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
                jsonPath("$.size()") { value(2) }
                jsonPath("$[0].id") { value(1) }
                jsonPath("$[0].userId") { value(1) }
                jsonPath("$[0].isEquipped") { value(false) }
                jsonPath("$[1].id") { value(2) }
                jsonPath("$[1].userId") { value(1) }
                jsonPath("$[1].isEquipped") { value(true) }
            }
    }

    @Test
    @DisplayName("PUT /api/user-emojis/{id} - 사용자 이모지 수정 성공")
    fun `사용자 이모지를 수정하면 200 OK와 수정된 이모지를 반환한다`() {
        // Given
        val updateCommand = UpdateUserEmojiCommand(
            id = 1L,
            userId = 1L,
            emojiId = 102L,
            isEquipped = true
        )
        val updatedUserEmoji = UserEmojiModel(
            id = 1L,
            userId = 1L,
            emojiId = updateCommand.emojiId,
            isEquipped = updateCommand.isEquipped
        )
        given(userEmojiService.updateUserEmoji(updateCommand)).willReturn(updatedUserEmoji)

        // When & Then
        mockMvc.put("/api/user-emojis/1") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(updateCommand.copy(id = 1L))
        }.andExpect {
            status { isOk() }
            content { contentType(MediaType.APPLICATION_JSON) }
            jsonPath("$.id") { value(1) }
            jsonPath("$.emojiId") { value(102) }
            jsonPath("$.isEquipped") { value(true) }
        }
    }

    @Test
    @DisplayName("DELETE /api/user-emojis/{id} - 사용자 이모지 삭제 성공")
    fun `사용자 이모지를 삭제하면 200 OK와 성공 메시지를 반환한다`() {
        // Given
        given(userEmojiService.deleteUserEmoji(1L)).willReturn(true)

        // When & Then
        mockMvc.delete("/api/user-emojis/1")
            .andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
            }
    }

    @Test
    @DisplayName("GET /api/user-emojis/{id} - 사용자 이모지 조회 실패 (404)")
    fun `존재하지 않는 사용자 이모지를 조회하면 404 NOT_FOUND를 반환한다`() {
        // Given
        given(userEmojiService.getUserEmojiById(999L)).willReturn(null)

        // When & Then
        mockMvc.get("/api/user-emojis/999")
            .andExpect {
                status { isNotFound() }
            }
    }

    @Test
    @DisplayName("PUT /api/user-emojis/{id} - 사용자 이모지 수정 실패 (404)")
    fun `존재하지 않는 사용자 이모지를 수정하면 404 NOT_FOUND를 반환한다`() {
        // Given
        val updateCommand = UpdateUserEmojiCommand(
            id = 999L,
            userId = 1L,
            emojiId = 102L,
            isEquipped = true
        )
        given(userEmojiService.updateUserEmoji(updateCommand)).willReturn(null)

        // When & Then
        mockMvc.put("/api/user-emojis/999") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(updateCommand.copy(id = 999L))
        }.andExpect {
            status { isNotFound() }
        }
    }

    @Test
    @DisplayName("DELETE /api/user-emojis/{id} - 사용자 이모지 삭제 실패 (404)")
    fun `존재하지 않는 사용자 이모지를 삭제하면 404 NOT_FOUND를 반환한다`() {
        // Given
        given(userEmojiService.deleteUserEmoji(999L)).willReturn(false)

        // When & Then
        mockMvc.delete("/api/user-emojis/999")
            .andExpect {
                status { isNotFound() }
            }
    }
}