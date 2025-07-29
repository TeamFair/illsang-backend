package com.illsang.quest.controller

import com.illsang.common.enums.ResponseMsg
import com.illsang.quest.domain.model.QuestModel
import com.illsang.quest.dto.request.QuestCreateRequest
import com.illsang.quest.dto.request.QuestUpdateRequest
import com.illsang.quest.dto.response.QuestResponse
import com.illsang.quest.service.QuestService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/quest")
@Tag(name = "Quest", description = "퀘스트")
class QuestController(
    private val questService: QuestService
) {

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(operationId = "QUE001", summary= "퀘스트 단일 조회")
    fun selectQuest(
        @PathVariable id: Long,
    ): ResponseEntity<QuestResponse> {
        val quest = this.questService.getQuest(id)

        return ResponseEntity.ok(
            QuestResponse.from(quest)
        )
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(operationId = "QUE002", summary= "퀘스트 생성")
    fun createQuest(
        @RequestBody request: QuestCreateRequest,
    ): ResponseEntity<QuestResponse> {
        val quest = this.questService.createQuest(request)

        return ResponseEntity.status(HttpStatus.CREATED).body(
            QuestResponse.from(quest)
        )
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(operationId = "QUE003", summary= "퀘스트 수정")
    fun updateQuest(
        @PathVariable id: Long,
        @RequestBody request: QuestUpdateRequest,
    ): ResponseEntity<QuestResponse> {
        val quest = this.questService.updateQuest(id, request)

        return ResponseEntity.status(HttpStatus.CREATED).body(
            QuestResponse.from(quest)
        )
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(operationId = "QUE004", summary= "퀘스트 삭제")
    fun deleteQuest(
        @PathVariable id: Long,
    ): ResponseEntity<ResponseMsg> {
        questService.deleteQuest(id)

        return ResponseEntity.ok().build()
    }

}
