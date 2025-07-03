package com.illsang.moduleuser.adapter.`in`.web

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.illsang.moduleuser.adapter.`in`.web.model.request.AddPointRequest
import com.illsang.moduleuser.adapter.`in`.web.model.request.CreateUserXpRequest
import com.illsang.moduleuser.adapter.`in`.web.model.request.UpdateUserXpRequest
import com.illsang.moduleuser.application.command.CreateUserXpCommand
import com.illsang.moduleuser.application.command.UpdateUserXpCommand
import com.illsang.moduleuser.application.service.UserXpService
import com.illsang.moduleuser.domain.model.UserXpModel
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
@WebMvcTest(UserXpController::class)
class UserXpControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockitoBean
    private lateinit var userXpService: UserXpService

    private val objectMapper = jacksonObjectMapper()

    @Test
    @DisplayName("POST /api/user-xps - 사용자 경험치 생성 성공")
    fun `사용자 경험치를 생성하면 201 CREATED와 생성된 경험치를 반환한다`() {
        // Given
        val request = CreateUserXpRequest(
            userId = 1L,
            xpType = XpType.QUEST,
            point = 100
        )
        val command = CreateUserXpCommand(
            userId = request.userId,
            xpType = request.xpType,
            point = request.point
        )
        val createdUserXp = UserXpModel(
            id = 1L,
            userId = command.userId,
            xpType = command.xpType,
            point = command.point
        )
        given(userXpService.createUserXp(command)).willReturn(createdUserXp)

        // When & Then
        mockMvc.post("/api/user-xps") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(request)
        }.andExpect {
            status { isCreated() }
            content { contentType(MediaType.APPLICATION_JSON) }
        }
    }

    @Test
    @DisplayName("GET /api/user-xps/{id} - 사용자 경험치 단건 조회 성공")
    fun `사용자 경험치를 ID로 조회하면 200 OK와 상세 정보를 반환한다`() {
        // Given
        val userXp = UserXpModel(
            id = 1L,
            userId = 1L,
            xpType = XpType.QUEST,
            point = 100
        )
        given(userXpService.getUserXpById(1L)).willReturn(userXp)

        // When & Then
        mockMvc.get("/api/user-xps/1")
            .andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
                jsonPath("$.id") { value(1) }
                jsonPath("$.userId") { value(1) }
                jsonPath("$.xpType") { value("QUEST") }
                jsonPath("$.point") { value(100) }
            }
    }

    @Test
    @DisplayName("GET /api/user-xps/user/{userId} - 사용자별 경험치 목록 조회 성공")
    fun `특정 사용자의 경험치 목록을 조회하면 200 OK와 리스트를 반환한다`() {
        // Given
        val userXps = listOf(
            UserXpModel(
                id = 1L,
                userId = 1L,
                xpType = XpType.QUEST,
                point = 100
            ),
            UserXpModel(
                id = 2L,
                userId = 1L,
                xpType = XpType.MISSION,
                point = 50
            )
        )
        given(userXpService.getUserXpsByUserId(1L)).willReturn(userXps)

        // When & Then
        mockMvc.get("/api/user-xps/user/1")
            .andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
                jsonPath("$.size()") { value(2) }
            }
    }

    @Test
    @DisplayName("GET /api/user-xps/user/{userId}/type/{xpType} - 사용자 경험치 타입별 조회 성공")
    fun `사용자의 특정 타입 경험치를 조회하면 200 OK와 상세 정보를 반환한다`() {
        // Given
        val userXp = UserXpModel(
            id = 1L,
            userId = 1L,
            xpType = XpType.QUEST,
            point = 100
        )
        given(userXpService.getUserXpByUserIdAndXpType(1L, XpType.QUEST)).willReturn(userXp)

        // When & Then
        mockMvc.get("/api/user-xps/user/1/type/QUEST")
            .andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
            }
    }

    @Test
    @DisplayName("PUT /api/user-xps/{id} - 사용자 경험치 수정 성공")
    fun `사용자 경험치를 수정하면 200 OK와 수정된 경험치를 반환한다`() {
        // Given
        val request = UpdateUserXpRequest(
            xpType = XpType.MISSION,
            point = 200
        )
        val command = UpdateUserXpCommand(
            id = 1L,
            xpType = request.xpType,
            point = request.point
        )
        val updatedUserXp = UserXpModel(
            id = 1L,
            userId = 1L,
            xpType = command.xpType!!,
            point = command.point!!
        )
        given(userXpService.updateUserXp(command)).willReturn(updatedUserXp)

        // When & Then
        mockMvc.put("/api/user-xps/1") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(request)
        }.andExpect {
            status { isOk() }
        }
    }

    @Test
    @DisplayName("POST /api/user-xps/user/{userId}/add-point - 사용자 경험치 포인트 추가 성공")
    fun `사용자 경험치에 포인트를 추가하면 200 OK와 업데이트된 경험치를 반환한다`() {
        // Given
        val request = AddPointRequest(
            xpType = XpType.QUEST,
            additionalPoint = 50
        )
        val updatedUserXp = UserXpModel(
            id = 1L,
            userId = 1L,
            xpType = XpType.QUEST,
            point = 150
        )
        given(userXpService.addPoint(1L, request.xpType, request.additionalPoint)).willReturn(updatedUserXp)

        // When & Then
        mockMvc.post("/api/user-xps/user/1/add-point") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(request)
        }.andExpect {
            status { isOk() }
            content { contentType(MediaType.APPLICATION_JSON) }
        }
    }

    @Test
    @DisplayName("DELETE /api/user-xps/{id} - 사용자 경험치 삭제 성공")
    fun `사용자 경험치를 삭제하면 200 OK와 성공 메시지를 반환한다`() {
        // Given
        given(userXpService.deleteUserXp(1L)).willReturn(true)

        // When & Then
        mockMvc.delete("/api/user-xps/1")
            .andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
            }
    }

    @Test
    @DisplayName("GET /api/user-xps/{id} - 사용자 경험치 조회 실패 (404)")
    fun `존재하지 않는 사용자 경험치를 조회하면 404 NOT_FOUND를 반환한다`() {
        // Given
        given(userXpService.getUserXpById(999L)).willReturn(null)

        // When & Then
        mockMvc.get("/api/user-xps/999")
            .andExpect {
                status { isNotFound() }
            }
    }

    @Test
    @DisplayName("GET /api/user-xps/user/{userId}/type/{xpType} - 사용자 경험치 타입별 조회 실패 (404)")
    fun `존재하지 않는 사용자 경험치 타입을 조회하면 404 NOT_FOUND를 반환한다`() {
        // Given
        given(userXpService.getUserXpByUserIdAndXpType(1L, XpType.QUEST)).willReturn(null)

        // When & Then
        mockMvc.get("/api/user-xps/user/1/type/QUEST")
            .andExpect {
                status { isNotFound() }
            }
    }

    @Test
    @DisplayName("PUT /api/user-xps/{id} - 사용자 경험치 수정 실패 (404)")
    fun `존재하지 않는 사용자 경험치를 수정하면 404 NOT_FOUND를 반환한다`() {
        // Given
        val request = UpdateUserXpRequest(
            xpType = XpType.MISSION,
            point = 200
        )
        val command = UpdateUserXpCommand(
            id = 999L,
            xpType = request.xpType,
            point = request.point
        )
        given(userXpService.updateUserXp(command)).willReturn(null)

        // When & Then
        mockMvc.put("/api/user-xps/999") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(request)
        }.andExpect {
            status { isNotFound() }
        }
    }

    @Test
    @DisplayName("POST /api/user-xps/user/{userId}/add-point - 사용자 경험치 포인트 추가 실패 (404)")
    fun `존재하지 않는 사용자 경험치에 포인트를 추가하면 404 NOT_FOUND를 반환한다`() {
        // Given
        val request = AddPointRequest(
            xpType = XpType.QUEST,
            additionalPoint = 50
        )
        given(userXpService.addPoint(999L, request.xpType, request.additionalPoint)).willReturn(null)

        // When & Then
        mockMvc.post("/api/user-xps/user/999/add-point") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(request)
        }.andExpect {
            status { isNotFound() }
        }
    }

    @Test
    @DisplayName("DELETE /api/user-xps/{id} - 사용자 경험치 삭제 실패 (404)")
    fun `존재하지 않는 사용자 경험치를 삭제하면 404 NOT_FOUND를 반환한다`() {
        // Given
        given(userXpService.deleteUserXp(999L)).willReturn(false)

        // When & Then
        mockMvc.delete("/api/user-xps/999")
            .andExpect {
                status { isNotFound() }
            }
    }
}
