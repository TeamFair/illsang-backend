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
            .addOpenApiCustomizer(this::applyPageableCustomizer)
            .build()
    }

    @Bean
    fun adminResource(): GroupedOpenApi {
        return GroupedOpenApi.builder()
            .group("admin-resource")
            .displayName("2. Admin Resource")
            .addOpenApiMethodFilter(::isAdminMethod)
            .addOpenApiCustomizer(this::applyPageableCustomizer)
            .build()
    }

    @Bean
    fun userResource(): GroupedOpenApi {
        return GroupedOpenApi.builder()
            .group("user-resource")
            .displayName("3. User Resource")
            .addOpenApiMethodFilter(::isUserMethod)
            .addOpenApiCustomizer(this::applyPageableCustomizer)
            .build()
    }

    @Bean
    fun openResource(): GroupedOpenApi {
        return GroupedOpenApi.builder()
            .group("open-resource")
            .displayName("4. Open Resource")
            .addOpenApiMethodFilter(::isPublicMethod)
            .addOpenApiCustomizer(this::applyPageableCustomizer)
            .build()
    }

    /**
     * Page 객체의 OpenAPI 스키마를 간결한 형태로 변환하는 공통 Customizer 로직입니다.
     */
    private fun applyPageableCustomizer(openApi: OpenAPI) {
        val schemas = openApi.components.schemas ?: return

        // 구조(프로퍼티)를 기반으로 Page 스키마를 식별합니다.
        val pageSchemas = schemas.filter { (_, schema) ->
            val props = schema.properties
            props != null && props.containsKey("pageable") && props.containsKey("totalPages") && props.containsKey("sort")
        }

        pageSchemas.forEach { (_, schema) ->
            val props = schema.properties ?: return@forEach

            props.remove("number")?.let {
                it.description("현재 페이지 번호 (0부터 시작)")
                props["page"] = it
            }

            props.remove("last")?.let {
                it.description("마지막 페이지 여부")
                props["isLast"] = it
            }

            props.remove("pageable")
            props.remove("sort")
            props.remove("first")
            props.remove("empty")
            props.remove("numberOfElements")

            schema.description("페이징 처리 응답 모델")
        }
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
