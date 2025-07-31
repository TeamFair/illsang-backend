package com.illsang.quest.controller

import com.illsang.quest.dto.request.quest.QuestRewardCreateRequest
import com.illsang.quest.dto.request.quest.QuestRewardUpdateRequest
import com.illsang.quest.dto.response.quest.QuestRewardResponse
import com.illsang.quest.service.quest.QuestRewardService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/quest/reward")
@Tag(name = "Quest Reward", description = "퀘스트 보상")
class QuestRewardController(
    private val questRewardService: QuestRewardService,
) {

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(operationId = "REW001", summary= "퀘스트 보상 생성")
    fun createQuestReward(
        @RequestBody request: QuestRewardCreateRequest,
    ): ResponseEntity<QuestRewardResponse> {
        val questReward = this.questRewardService.createQuestReward(request)

        return ResponseEntity.status(HttpStatus.CREATED).body(
            QuestRewardResponse.from(questReward)
        )
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(operationId = "REW002", summary= "퀘스트 보상 단일 조회")
    fun getQuestReward(
        @PathVariable id: Long,
    ): ResponseEntity<QuestRewardResponse> {
        val questReward = this.questRewardService.getQuestRewardById(id)

        return ResponseEntity.ok(
            QuestRewardResponse.from(questReward)
        )
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(operationId = "REW003", summary= "퀘스트 보상 수정")
    fun updateQuestReward(
        @PathVariable id: Long,
        @RequestBody request: QuestRewardUpdateRequest,
    ): ResponseEntity<QuestRewardResponse> {
        val questReward = this.questRewardService.updateQuestReward(id, request)

        return ResponseEntity.ok(
            QuestRewardResponse.from(questReward)
        )
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(operationId = "REW004", summary= "퀘스트 보상 삭제")
    fun deleteQuestReward(
        @PathVariable id: Long,
    ): ResponseEntity<Void> {
        this.questRewardService.deleteQuestReward(id)

        return ResponseEntity.ok().build()
    }

}
