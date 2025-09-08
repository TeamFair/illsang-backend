package com.illsang.user.controller

import com.illsang.auth.domain.model.AuthenticationModel
import com.illsang.user.dto.response.UserTitleForLegendResponse
import com.illsang.user.dto.response.UserTitleResponse
import com.illsang.user.service.UserTitleService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springdoc.core.annotations.ParameterObject
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/user/title")
@Tag(name = "User Title", description = "유저 칭호")
class UserTitleController (
    private val userTitleService: UserTitleService
){

    @GetMapping
    @PreAuthorize("hasAnyRole('USER')")
    @Operation(operationId="UST001", summary="사용자 칭호 조회")
    fun getUserTitle(
        @AuthenticationPrincipal auth: AuthenticationModel
    ) : ResponseEntity<List<UserTitleResponse>>{
        val userTitles = userTitleService.getTitlesByUserId(auth.userId)
        return ResponseEntity.ok(userTitles.map{ UserTitleResponse.from(it)})
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER')")
    @Operation(operationId="UST002", summary="사용자 칭호 상세 조회")
    fun getUserTitleDetail(
        @PathVariable id: Long,
    ) : ResponseEntity<UserTitleResponse>{
        val userTitle = userTitleService.getTitle(id)
        return ResponseEntity.ok(UserTitleResponse.from(userTitle))
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('USER')")
    @Operation(operationId = "UST003", summary = "사용자 칭호 부여")
    fun assignUserTitle(
        @RequestParam userId: String,
        @RequestParam titleId: String,
    ): ResponseEntity<Void> {
        userTitleService.createUserTitle(userId, titleId)
        return ResponseEntity.ok().build()
    }

    @PutMapping("/{id}/read")
    @PreAuthorize("hasAnyRole('USER')")
    @Operation(operationId = "UST004", summary = "사용자 칭호 읽음 처리")
    fun updateTitleReadStatus(
        @PathVariable id: Long
    ): ResponseEntity<Void> {
        userTitleService.updateReadStatus(id)
        return ResponseEntity.ok().build()
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER')")
    @Operation(operationId = "UST005", summary = "사용자 칭호 삭제")
    fun deleteUserTitle(
        @PathVariable id: Long
    ): ResponseEntity<Void> {
        userTitleService.deleteUserTitle(id)
        return ResponseEntity.ok().build()
    }

    @GetMapping("/unread")
    @PreAuthorize("hasAnyRole('USER')")
    @Operation(operationId = "UST006", summary = "읽지않은 사용자 칭호 조회")
    fun getUnreadTitle(
        @AuthenticationPrincipal auth: AuthenticationModel
    ): ResponseEntity<List<UserTitleResponse>> {
        val userTitles = userTitleService.getUnreadTitle(auth.userId)
        return ResponseEntity.ok(userTitles.map { UserTitleResponse.from(it) })
    }

    @GetMapping("/legend")
    @PreAuthorize("hasAnyRole('USER')")
    @Operation(operationId = "UST007", summary = "전설칭호 조회")
    fun getLegendTitleList(
        @ParameterObject @PageableDefault(size = 10) pageable: Pageable,
        @RequestParam titleId: String?,
    ): ResponseEntity<Page<UserTitleForLegendResponse>> {
        val userTitles = userTitleService.getAllLegendTitle(pageable, titleId)
        val userResponse = userTitles.map { UserTitleForLegendResponse.from(it) }
        return ResponseEntity.ok(userResponse)
    }


}