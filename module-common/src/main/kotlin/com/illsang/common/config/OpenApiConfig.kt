package com.illsang.common.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.security.*
import jakarta.annotation.security.PermitAll
import org.springdoc.core.models.GroupedOpenApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.access.prepost.PreAuthorize
import java.lang.reflect.Method

@Configuration
class OpenApiConfig {

    @Bean
    fun customOpenAPI(): OpenAPI {
        return OpenAPI()
            .components(
                Components()
                    .addSecuritySchemes("Google OAuth2",
                        SecurityScheme()
                            .type(SecurityScheme.Type.OAUTH2)
                            .flows(
                                OAuthFlows()
                                    .implicit(
                                        OAuthFlow()
                                            .authorizationUrl("https://accounts.google.com/o/oauth2/v2/auth")
                                            .scopes(
                                                Scopes()
                                                    .addString("openid", "OpenID Connect")
                                                    .addString("email", "Email access")
                                                    .addString("profile", "Profile access")
                                            )
                                    )
                            )
                            .description("Google OAuth2 Implicit Flow - 관리자 전용 토큰")
                    )
                    .addSecuritySchemes("JWT",
                        SecurityScheme()
                            .type(SecurityScheme.Type.HTTP)
                            .scheme("bearer")
                            .bearerFormat("JWT")
                            .`in`(SecurityScheme.In.HEADER)
                            .description("일상 JWT 토큰 (SNS OAuth 로그인 후 받은 JWT 토큰)")
                    )
            )
            .addSecurityItem(SecurityRequirement().addList("Google OAuth2"))
            .addSecurityItem(SecurityRequirement().addList("JWT"))
    }

    @Bean
    fun allResource(): GroupedOpenApi {
        return GroupedOpenApi.builder()
            .group("all-resource")
            .displayName("1. All Resource")
            .pathsToMatch("/**")
            .build()
    }

    @Bean
    fun adminResource(): GroupedOpenApi {
        return GroupedOpenApi.builder()
            .group("admin-resource")
            .displayName("2. Admin Resource")
            .addOpenApiMethodFilter(::isAdminMethod)
            .build()
    }

    @Bean
    fun userResource(): GroupedOpenApi {
        return GroupedOpenApi.builder()
            .group("user-resource")
            .displayName("3. User Resource")
            .addOpenApiMethodFilter(::isUserMethod)
            .build()
    }

    @Bean
    fun openResource(): GroupedOpenApi {
        return GroupedOpenApi.builder()
            .group("open-resource")
            .displayName("4. Open Resource")
            .addOpenApiMethodFilter(::isPublicMethod)
            .build()
    }

    private fun isAdminMethod(method: Method): Boolean {
        val preAuth = method.getAnnotation(PreAuthorize::class.java)
        return preAuth?.value?.contains("ADMIN") == true || method.getAnnotation(PermitAll::class.java) != null
    }

    private fun isUserMethod(method: Method): Boolean {
        val preAuth = method.getAnnotation(PreAuthorize::class.java)
        return preAuth?.value?.contains("USER") == true || method.getAnnotation(PermitAll::class.java) != null
    }

    private fun isPublicMethod(method: Method): Boolean {
        return method.getAnnotation(PreAuthorize::class.java) == null && method.getAnnotation(PermitAll::class.java) == null
    }
}
