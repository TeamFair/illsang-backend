package com.illsang.quest.controller.user

import com.illsang.auth.domain.model.AuthenticationModel
import com.illsang.quest.dto.request.user.QuestUserFavoriteCreateRequest
import com.illsang.quest.dto.request.user.QuestUserFavoriteDeleteRequest
import com.illsang.quest.service.user.UserQuestFavoriteService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/vi/quest/user/favorite")
@Tag(name = "Quest User Favorite", description = "사용자 퀘스트 즐겨찾기")
class QuestUserFavoriteController(
    private val questUserQuestFavoriteService: UserQuestFavoriteService,
) {

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    @Operation(operationId = "QUF001", summary = "사용자 퀘스트 즐겨찾기 등록")
    fun createFavorite(
        @RequestBody request: QuestUserFavoriteCreateRequest,
        @AuthenticationPrincipal authenticationModel: AuthenticationModel,
    ): ResponseEntity<Void> {
        this.questUserQuestFavoriteService.createFavorite(authenticationModel.userId, request.questId)

        return ResponseEntity.ok().build()
    }

    @DeleteMapping
    @PreAuthorize("hasRole('USER')")
    @Operation(operationId = "QUF002", summary = "사용자 퀘스트 즐겨찾기 삭제")
    fun deleteFavorite(
        @RequestBody request: QuestUserFavoriteDeleteRequest,
        @AuthenticationPrincipal authenticationModel: AuthenticationModel,
    ): ResponseEntity<Void> {
        this.questUserQuestFavoriteService.deleteFavorite(authenticationModel.userId, request.questId)

        return ResponseEntity.ok().build()
    }

}
