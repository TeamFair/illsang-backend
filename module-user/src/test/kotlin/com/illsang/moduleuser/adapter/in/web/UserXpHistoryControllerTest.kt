package com.illsang.moduleuser.adapter.`in`.web

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.illsang.moduleuser.adapter.`in`.web.model.request.CreateUserXpHistoryRequest
import com.illsang.moduleuser.adapter.`in`.web.model.request.UpdateUserXpHistoryRequest
import com.illsang.moduleuser.application.command.CreateUserXpHistoryCommand
import com.illsang.moduleuser.application.command.UpdateUserXpHistoryCommand
import com.illsang.moduleuser.application.service.UserXpHistoryService
import com.illsang.moduleuser.domain.model.UserXpHistoryModel
import com.illsang.moduleuser.domain.model.XpType
import org.junit.jupiter.api.DisplayName
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.*
import kotlin.test.Test

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(UserXpHistoryController::class)
class UserXpHistoryControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockitoBean
    private lateinit var userXpHistoryService: UserXpHistoryService

    private val objectMapper = jacksonObjectMapper()

    @Test
    @DisplayName("POST /api/user-xp-histories - 사용자 경험치 이력 생성 성공")
    fun `사용자 경험치 이력을 생성하면 201 CREATED와 생성된 이력을 반환한다`() {
        // Given
        val request = CreateUserXpHistoryRequest(
            userId = 1L,
            xpType = XpType.QUEST,
            point = 100
        )
        val command = CreateUserXpHistoryCommand(
            userId = request.userId,
            xpType = request.xpType,
            point = request.point
        )
        val createdUserXpHistory = UserXpHistoryModel(
            id = 1L,
            userId = command.userId,
            xpType = command.xpType,
            point = command.point
        )
        given(userXpHistoryService.createUserXpHistory(command)).willReturn(createdUserXpHistory)

        // When & Then
        mockMvc.post("/api/user-xp-histories") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(request)
        }.andExpect {
            status { isCreated() }
            content { contentType(MediaType.APPLICATION_JSON) }
        }
    }

    @Test
    @DisplayName("GET /api/user-xp-histories/{id} - 사용자 경험치 이력 단건 조회 성공")
    fun `사용자 경험치 이력을 ID로 조회하면 200 OK와 상세 정보를 반환한다`() {
        // Given
        val userXpHistory = UserXpHistoryModel(
            id = 1L,
            userId = 1L,
            xpType = XpType.QUEST,
            point = 100
        )
        given(userXpHistoryService.getUserXpHistoryById(1L)).willReturn(userXpHistory)

        // When & Then
        mockMvc.get("/api/user-xp-histories/1")
            .andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
            }
    }

    @Test
    @DisplayName("GET /api/user-xp-histories/user/{userId} - 사용자별 경험치 이력 목록 조회 성공")
    fun `특정 사용자의 경험치 이력 목록을 조회하면 200 OK와 리스트를 반환한다`() {
        // Given
        val userXpHistories = listOf(
            UserXpHistoryModel(
                id = 1L,
                userId = 1L,
                xpType = XpType.QUEST,
                point = 100
            ),
            UserXpHistoryModel(
                id = 2L,
                userId = 1L,
                xpType = XpType.MISSION,
                point = 50
            )
        )
        given(userXpHistoryService.getUserXpHistoriesByUserId(1L)).willReturn(userXpHistories)

        // When & Then
        mockMvc.get("/api/user-xp-histories/user/1")
            .andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
                jsonPath("$.size()") { value(2) }
            }
    }

    @Test
    @DisplayName("GET /api/user-xp-histories/user/{userId}/type/{xpType} - 사용자 경험치 이력 타입별 조회 성공")
    fun `사용자의 특정 타입 경험치 이력을 조회하면 200 OK와 리스트를 반환한다`() {
        // Given
        val userXpHistories = listOf(
            UserXpHistoryModel(
                id = 1L,
                userId = 1L,
                xpType = XpType.QUEST,
                point = 100
            ),
            UserXpHistoryModel(
                id = 2L,
                userId = 1L,
                xpType = XpType.QUEST,
                point = 50
            )
        )
        given(userXpHistoryService.getUserXpHistoriesByUserIdAndXpType(1L, XpType.QUEST)).willReturn(userXpHistories)

        // When & Then
        mockMvc.get("/api/user-xp-histories/user/1/type/QUEST")
            .andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
                jsonPath("$.size()") { value(2) }
            }
    }

    @Test
    @DisplayName("PUT /api/user-xp-histories/{id} - 사용자 경험치 이력 수정 성공")
    fun `사용자 경험치 이력을 수정하면 200 OK와 수정된 이력을 반환한다`() {
        // Given
        val request = UpdateUserXpHistoryRequest(
            xpType = XpType.MISSION,
            point = 200
        )
        val command = UpdateUserXpHistoryCommand(
            id = 1L,
            xpType = request.xpType,
            point = request.point
        )
        val updatedUserXpHistory = UserXpHistoryModel(
            id = 1L,
            userId = 1L,
            xpType = command.xpType!!,
            point = command.point!!
        )
        given(userXpHistoryService.updateUserXpHistory(command)).willReturn(updatedUserXpHistory)

        // When & Then
        mockMvc.put("/api/user-xp-histories/1") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(request)
        }.andExpect {
            status { isOk() }
            content { contentType(MediaType.APPLICATION_JSON) }
        }
    }

    @Test
    @DisplayName("DELETE /api/user-xp-histories/{id} - 사용자 경험치 이력 삭제 성공")
    fun `사용자 경험치 이력을 삭제하면 200 OK와 성공 메시지를 반환한다`() {
        // Given
        given(userXpHistoryService.deleteUserXpHistory(1L)).willReturn(true)

        // When & Then
        mockMvc.delete("/api/user-xp-histories/1")
            .andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
            }
    }

    @Test
    @DisplayName("GET /api/user-xp-histories/{id} - 사용자 경험치 이력 조회 실패 (404)")
    fun `존재하지 않는 사용자 경험치 이력을 조회하면 404 NOT_FOUND를 반환한다`() {
        // Given
        given(userXpHistoryService.getUserXpHistoryById(999L)).willReturn(null)

        // When & Then
        mockMvc.get("/api/user-xp-histories/999")
            .andExpect {
                status { isNotFound() }
            }
    }

    @Test
    @DisplayName("PUT /api/user-xp-histories/{id} - 사용자 경험치 이력 수정 실패 (404)")
    fun `존재하지 않는 사용자 경험치 이력을 수정하면 404 NOT_FOUND를 반환한다`() {
        // Given
        val request = UpdateUserXpHistoryRequest(
            xpType = XpType.MISSION,
            point = 200
        )
        val command = UpdateUserXpHistoryCommand(
            id = 999L,
            xpType = request.xpType,
            point = request.point
        )
        given(userXpHistoryService.updateUserXpHistory(command)).willReturn(null)

        // When & Then
        mockMvc.put("/api/user-xp-histories/999") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(request)
        }.andExpect {
            status { isNotFound() }
        }
    }

    @Test
    @DisplayName("DELETE /api/user-xp-histories/{id} - 사용자 경험치 이력 삭제 실패 (404)")
    fun `존재하지 않는 사용자 경험치 이력을 삭제하면 404 NOT_FOUND를 반환한다`() {
        // Given
        given(userXpHistoryService.deleteUserXpHistory(999L)).willReturn(false)

        // When & Then
        mockMvc.delete("/api/user-xp-histories/999")
            .andExpect {
                status { isNotFound() }
            }
    }
}
