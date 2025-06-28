package com.illsang.management.adapter.`in`.web

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.illsang.management.application.command.CreateBannerCommand
import com.illsang.management.application.command.UpdateBannerCommand
import com.illsang.management.application.service.BannerService
import com.illsang.management.domain.model.BannerModel
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
import kotlin.test.Test

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(BannerController::class)
class BannerControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockitoBean
    private lateinit var bannerService: BannerService

    private val objectMapper = jacksonObjectMapper()

    @Test
    @DisplayName("POST /api/v1/banners - 배너 생성 성공")
    fun `배너를 생성하면 201 CREATED와 생성된 배너를 반환한다`() {
        // Given
        val command = CreateBannerCommand(
            title = "이벤트 배너",
            bannerImageId = 1001L,
            description = "여름 프로모션",
            activeYn = true,
        )
        val createdBanner = BannerModel(
            id = 1L,
            title = command.title,
            bannerImageId = command.bannerImageId,
            description = command.description,
            activeYn = command.activeYn,
        )
        given(bannerService.createBanner(command)).willReturn(createdBanner)

        // When & Then
        mockMvc.post("/api/v1/banners") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(command)
        }.andExpect {
            status { isCreated() }
            content { contentType(MediaType.APPLICATION_JSON) }
            jsonPath("$.id") { value(1) }
            jsonPath("$.title") { value(command.title) }
        }
    }

    @Test
    @DisplayName("GET /api/v1/banners/{id} - 배너 단건 조회 성공")
    fun `배너를 ID로 조회하면 200 OK와 상세 정보를 반환한다`() {
        // Given
        val banner = BannerModel(
            id = 1L,
            title = "이벤트 배너",
            bannerImageId = 1001L,
            description = "여름 프로모션",
            activeYn = true,
            createdBy = "admin",
            createdAt = LocalDateTime.of(2024, 6, 1, 10, 0)
        )
        given(bannerService.getBanner(1L)).willReturn(banner)

        // When & Then
        mockMvc.get("/api/v1/banners/1")
            .andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
                jsonPath("$.id") { value(1) }
                jsonPath("$.title") { value("이벤트 배너") }
            }
    }

    @Test
    @DisplayName("GET /api/v1/banners - 배너 전체 목록 조회 성공")
    fun `배너 전체 목록을 조회하면 200 OK와 리스트를 반환한다`() {
        // Given
        val banners = listOf(
            BannerModel(
                id = 1L,
                title = "이벤트 배너",
                bannerImageId = 1001L,
                description = "여름 프로모션",
                activeYn = true,
                createdBy = "admin",
                createdAt = LocalDateTime.of(2024, 6, 1, 10, 0)
            ),
            BannerModel(
                id = 2L,
                title = "공지사항 배너",
                bannerImageId = 1002L,
                description = "점검 안내",
                activeYn = false,
                createdBy = "system",
                createdAt = LocalDateTime.of(2024, 6, 2, 15, 30)
            )
        )
        given(bannerService.getAllBanners()).willReturn(banners)

        // When & Then
        mockMvc.get("/api/v1/banners")
            .andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
                jsonPath("$.size()") { value(2) }
                jsonPath("$[0].id") { value(1) }
                jsonPath("$[1].id") { value(2) }
            }
    }

    @Test
    @DisplayName("GET /api/v1/banners/active - 활성 배너 목록 조회 성공")
    fun `활성 배너 목록을 조회하면 200 OK와 리스트를 반환한다`() {
        // Given
        val banners = listOf(
            BannerModel(
                id = 1L,
                title = "이벤트 배너",
                bannerImageId = 1001L,
                description = "여름 프로모션",
                activeYn = true,
                createdBy = "admin",
                createdAt = LocalDateTime.of(2024, 6, 1, 10, 0)
            )
        )
        given(bannerService.getActiveBanners()).willReturn(banners)

        // When & Then
        mockMvc.get("/api/v1/banners/active")
            .andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
                jsonPath("$.size()") { value(1) }
                jsonPath("$[0].activeYn") { value(true) }
            }
    }

    @Test
    @DisplayName("PUT /api/v1/banners/{id} - 배너 수정 성공")
    fun `배너를 수정하면 200 OK와 수정된 배너를 반환한다`() {
        // Given
        val updateCommand = UpdateBannerCommand(
            id = 1L,
            title = "수정된 배너",
            bannerImageId = 1001L,
            description = "수정된 설명",
            activeYn = false,
        )
        val updatedBanner = BannerModel(
            id = 1L,
            title = updateCommand.title!!,
            bannerImageId = updateCommand.bannerImageId,
            description = updateCommand.description,
            activeYn = updateCommand.activeYn == true,
            createdBy = "admin",
        )
        given(bannerService.updateBanner(updateCommand)).willReturn(updatedBanner)

        // When & Then
        mockMvc.put("/api/v1/banners/1") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(updateCommand.copy(id = 1L))
        }.andExpect {
            status { isOk() }
            content { contentType(MediaType.APPLICATION_JSON) }
            jsonPath("$.id") { value(1) }
            jsonPath("$.title") { value("수정된 배너") }
            jsonPath("$.description") { value("수정된 설명") }
        }
    }

    @Test
    @DisplayName("DELETE /api/v1/banners/{id} - 배너 삭제 성공")
    fun `배너를 삭제하면 200 OK와 성공 메시지를 반환한다`() {
        // Given
        doNothing().`when`(bannerService).deleteBanner(1L)

        // When & Then
        mockMvc.delete("/api/v1/banners/1")
            .andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
            }
    }
}