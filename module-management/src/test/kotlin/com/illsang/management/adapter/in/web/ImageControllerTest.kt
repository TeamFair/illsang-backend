package com.illsang.management.adapter.`in`.web

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.illsang.management.application.command.CreateImageCommand
import com.illsang.management.application.command.UpdateImageCommand
import com.illsang.management.application.service.ImageService
import com.illsang.management.domain.model.ImageModel
import org.junit.jupiter.api.DisplayName
import org.mockito.BDDMockito.given
import org.mockito.Mockito.doNothing
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.*
import kotlin.test.Test

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(ImageController::class)
class ImageControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockitoBean
    private lateinit var imageService: ImageService

    private val objectMapper = jacksonObjectMapper()

    @Test
    @DisplayName("POST /api/v1/images - 이미지 생성 성공")
    fun `이미지를 생성하면 201 CREATED와 생성된 이미지를 반환한다`() {
        // Given
        val command = CreateImageCommand(
            type = "BANNER",
            name = "test-image.jpg",
            size = 1024L
        )
        val createdImage = ImageModel(
            id = 1L,
            type = command.type,
            name = command.name,
            size = command.size
        )
        given(imageService.createImage(command)).willReturn(createdImage)

        // When & Then
        mockMvc.post("/api/v1/images") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(command)
        }.andExpect {
            status { isCreated() }
            content { contentType(MediaType.APPLICATION_JSON) }
            jsonPath("$.id") { value(1) }
            jsonPath("$.name") { value(command.name) }
            jsonPath("$.type") { value(command.type) }
        }
    }

    @Test
    @DisplayName("GET /api/v1/images/{id} - 이미지 단건 조회 성공")
    fun `이미지를 ID로 조회하면 200 OK와 상세 정보를 반환한다`() {
        // Given
        val image = ImageModel(
            id = 1L,
            type = "BANNER",
            name = "test-image.jpg",
            size = 1024L
        )
        given(imageService.getImage(1L)).willReturn(image)

        // When & Then
        mockMvc.get("/api/v1/images/1")
            .andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
                jsonPath("$.id") { value(1) }
                jsonPath("$.name") { value("test-image.jpg") }
                jsonPath("$.type") { value("BANNER") }
            }
    }

    @Test
    @DisplayName("GET /api/v1/images - 이미지 전체 목록 조회 성공")
    fun `이미지 전체 목록을 조회하면 200 OK와 리스트를 반환한다`() {
        // Given
        val images = listOf(
            ImageModel(
                id = 1L,
                type = "BANNER",
                name = "banner1.jpg",
                size = 1024L
            ),
            ImageModel(
                id = 2L,
                type = "PROFILE",
                name = "profile1.jpg",
                size = 2048L
            )
        )
        given(imageService.getAllImages()).willReturn(images)

        // When & Then
        mockMvc.get("/api/v1/images")
            .andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
                jsonPath("$.size()") { value(2) }
                jsonPath("$[0].id") { value(1) }
                jsonPath("$[0].type") { value("BANNER") }
                jsonPath("$[1].id") { value(2) }
                jsonPath("$[1].type") { value("PROFILE") }
            }
    }

    @Test
    @DisplayName("GET /api/v1/images/type/{type} - 타입별 이미지 목록 조회 성공")
    fun `특정 타입의 이미지 목록을 조회하면 200 OK와 리스트를 반환한다`() {
        // Given
        val images = listOf(
            ImageModel(
                id = 1L,
                type = "BANNER",
                name = "banner1.jpg",
                size = 1024L
            ),
            ImageModel(
                id = 2L,
                type = "BANNER",
                name = "banner2.jpg",
                size = 1536L
            )
        )
        given(imageService.getImagesByType("BANNER")).willReturn(images)

        // When & Then
        mockMvc.get("/api/v1/images/type/BANNER")
            .andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
                jsonPath("$.size()") { value(2) }
                jsonPath("$[0].type") { value("BANNER") }
                jsonPath("$[1].type") { value("BANNER") }
            }
    }

    @Test
    @DisplayName("PUT /api/v1/images/{id} - 이미지 수정 성공")
    fun `이미지를 수정하면 200 OK와 수정된 이미지를 반환한다`() {
        // Given
        val updateCommand = UpdateImageCommand(
            id = 1L,
            type = "PROFILE",
            name = "updated-image.jpg",
            size = 2048L
        )
        val updatedImage = ImageModel(
            id = 1L,
            type = updateCommand.type!!,
            name = updateCommand.name!!,
            size = updateCommand.size!!
        )
        given(imageService.updateImage(updateCommand)).willReturn(updatedImage)

        // When & Then
        mockMvc.put("/api/v1/images/1") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(updateCommand.copy(id = 1L))
        }.andExpect {
            status { isOk() }
            content { contentType(MediaType.APPLICATION_JSON) }
            jsonPath("$.id") { value(1) }
            jsonPath("$.name") { value("updated-image.jpg") }
            jsonPath("$.type") { value("PROFILE") }
        }
    }

    @Test
    @DisplayName("DELETE /api/v1/images/{id} - 이미지 삭제 성공")
    fun `이미지를 삭제하면 200 OK와 성공 메시지를 반환한다`() {
        // Given
        doNothing().`when`(imageService).deleteImage(1L)

        // When & Then
        mockMvc.delete("/api/v1/images/1")
            .andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
            }
    }
}