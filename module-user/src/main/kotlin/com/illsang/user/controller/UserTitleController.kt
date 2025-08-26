package com.illsang.user.controller

import com.illsang.auth.domain.model.AuthenticationModel
import com.illsang.user.dto.response.UserTitleResponse
import com.illsang.user.service.UserTitleService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/user/title")
@Tag(name = "User Title", description = "유저 칭호")
class UserTitleController (
    private val userTitleService: UserTitleService
){

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Operation(operationId="UST001", summary="사용자 칭호 조회")
    fun getUserTitle(
        @AuthenticationPrincipal auth: AuthenticationModel
    ) : ResponseEntity<List<UserTitleResponse>>{
        val userTitles = userTitleService.getTitlesByUserId(auth.userId)
        return ResponseEntity.ok(userTitles.map{ UserTitleResponse.from(it)})
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Operation(operationId="UST002", summary="사용자 칭호 상세 조회")
    fun getUserTitleDetail(
        @PathVariable id: Long,
    ) : ResponseEntity<UserTitleResponse>{
        val userTitle = userTitleService.getTitle(id)
        return ResponseEntity.ok(UserTitleResponse.from(userTitle))
    }
}