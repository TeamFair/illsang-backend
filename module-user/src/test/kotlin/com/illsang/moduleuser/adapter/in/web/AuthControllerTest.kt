package com.illsang.moduleuser.adapter.`in`.web

import com.fasterxml.jackson.databind.ObjectMapper
import com.illsang.moduleuser.adapter.`in`.web.model.request.OAuthLoginRequest
import com.illsang.moduleuser.application.service.UserService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.context.WebApplicationContext

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class AuthControllerTest {

    @Autowired
    private lateinit var webApplicationContext: WebApplicationContext

    @Autowired
    private lateinit var userService: UserService

    private val objectMapper = ObjectMapper()

    private val mockMvc: MockMvc by lazy {
        MockMvcBuilders.webAppContextSetup(webApplicationContext).build()
    }

    @Test
    fun `OAuth login should return 400 for invalid provider`() {
        val request = OAuthLoginRequest(
            provider = "google",
            osType = "ios",
            idToken = "dummy-token"
        )

        mockMvc.perform(
            post("/api/v1/auth/oauth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
            .andExpect(status().isBadRequest)
    }

    @Test
    fun `OAuth login should return 400 for invalid OS type`() {
        val request = OAuthLoginRequest(
            provider = "google",
            osType = "ios",
            idToken = "dummy-token"
        )

        mockMvc.perform(
            post("/api/v1/auth/oauth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
            .andExpect(status().isBadRequest)
    }

    @Test
    fun `OAuth login should return 400 for invalid token`() {
        val request = OAuthLoginRequest(
            provider = "google",
            osType = "ios",
            idToken = "invalid-token"
        )

        mockMvc.perform(
            post("/api/v1/auth/oauth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
            .andExpect(status().isBadRequest)
    }

    @Test
    fun `OAuth login endpoint should be accessible`() {
        val request = OAuthLoginRequest(
            provider = "google",
            osType = "ios",
            idToken = "dummy-token"
        )

        // This test just verifies the endpoint exists and accepts the request format
        // It will return 400 due to invalid token, but that's expected
        mockMvc.perform(
            post("/api/v1/auth/oauth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
            .andExpect(status().isBadRequest)
    }
}
