package com.illsang.common.config

import com.illsang.common.application.port.out.TokenPersistencePort
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.Instant

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestSecurityConfig::class)
class SecurityConfigTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockitoBean
    private lateinit var tokenPersistencePort: TokenPersistencePort

//    @BeforeEach
//    fun setUpMOckMvc() {
//        mockMvc = MockMvcBuilders
//            .webAppContextSetup(context)
//            .apply<DefaultMockMvcBuilder>(springSecurity())
//            .build()
//    }

    @Test
    @DisplayName("인증 없이 보호된 엔드포인트 접근 시 401 반환")
    fun `보호된 엔드포인트에 인증 없이 접근하면 401 Unauthorized를 반환한다`() {
        // When & Then
        mockMvc.perform(get("/api/protected"))
            .andDo(print()) // 실제 응답 전체 출력
            .andExpect(status().isUnauthorized)
    }

    @Test
    @DisplayName("유효한 JWT 토큰으로 인증 성공")
    fun `유효한 JWT 토큰으로 보호된 엔드포인트에 접근하면 인증이 성공한다`() {
        // Given
        val jwt = createMockJwt("user123", listOf("ROLE_CUSTOMER"))
        given(tokenPersistencePort.getAccessToken("user123")).willReturn("Bearer valid-token")

        // When & Then
        mockMvc.perform(get("/api/protected")
            .with(jwt().jwt(jwt))
            .header("authorization", "Bearer valid-token"))
            .andExpect(status().isNotFound)
    }

    @Test
    @DisplayName("CSRF 비활성화 확인")
    fun `CSRF가 비활성화되어 POST 요청이 CSRF 토큰 없이 가능하다`() {
        // Given
        val jwt = createMockJwt("user123", listOf("ROLE_CUSTOMER"))
        given(tokenPersistencePort.getAccessToken("user123")).willReturn("Bearer valid-token")

        // When & Then
        mockMvc.perform(post("/api/test")
            .with(jwt().jwt(jwt))
            .header("authorization", "Bearer valid-token"))
            .andExpect(status().isNotFound)
    }

    private fun createMockJwt(subject: String, authorities: List<String>): Jwt {
        return Jwt.withTokenValue("mock-token")
            .header("alg", "RS256")
            .subject(subject)
            .claim("authorities", authorities)
            .issuedAt(Instant.now())
            .expiresAt(Instant.now().plusSeconds(3600))
            .build()
    }
}
