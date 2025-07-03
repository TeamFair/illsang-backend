package com.illsang.modulequest.adapter.`in`.web

import com.illsang.common.enums.ResponseMsg
import com.illsang.modulequest.adapter.`in`.web.model.request.CreateQuestRewardRequest
import com.illsang.modulequest.adapter.`in`.web.model.request.UpdateQuestRewardRequest
import com.illsang.modulequest.adapter.`in`.web.model.response.QuestRewardResponse
import com.illsang.modulequest.application.command.CreateQuestRewardCommand
import com.illsang.modulequest.application.command.UpdateQuestRewardCommand
import com.illsang.modulequest.application.service.QuestRewardService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/quest-rewards")
class QuestRewardController(
    private val questRewardService: QuestRewardService
) {

    @PostMapping
    fun createQuestReward(@RequestBody request: CreateQuestRewardRequest): ResponseEntity<QuestRewardResponse> {
        val command = CreateQuestRewardCommand(
            type = request.type,
            amount = request.amount,
            questId = request.questId
        )
        val questReward = questRewardService.createQuestReward(command)
        return ResponseEntity.status(HttpStatus.CREATED).body(QuestRewardResponse.from(questReward))
    }

    @GetMapping("/{id}")
    fun getQuestReward(@PathVariable id: Long): ResponseEntity<QuestRewardResponse> {
        val questReward = questRewardService.getQuestRewardById(id)
        return if (questReward != null) {
            ResponseEntity.ok(QuestRewardResponse.from(questReward))
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @GetMapping
    fun getAllQuestRewards(): ResponseEntity<List<QuestRewardResponse>> {
        val questRewards = questRewardService.getAllQuestRewards()
        return ResponseEntity.ok(questRewards.map { QuestRewardResponse.from(it) })
    }

    @GetMapping("/quest/{questId}")
    fun getQuestRewardsByQuestId(@PathVariable questId: Long): ResponseEntity<List<QuestRewardResponse>> {
        val questRewards = questRewardService.getQuestRewardsByQuestId(questId)
        return ResponseEntity.ok(questRewards.map { QuestRewardResponse.from(it) })
    }

    @PutMapping("/{id}")
    fun updateQuestReward(
        @PathVariable id: Long,
        @RequestBody request: UpdateQuestRewardRequest
    ): ResponseEntity<QuestRewardResponse> {
        val command = UpdateQuestRewardCommand(
            id = id,
            type = request.type,
            amount = request.amount
        )
        val questReward = questRewardService.updateQuestReward(command)
        return if (questReward != null) {
            ResponseEntity.ok(QuestRewardResponse.from(questReward))
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/{id}")
    fun deleteQuestReward(@PathVariable id: Long): ResponseEntity<ResponseMsg> {
        val deleted = questRewardService.deleteQuestReward(id)
        return if (deleted) {
            ResponseEntity.ok(ResponseMsg.SUCCESS)
        } else {
            ResponseEntity.notFound().build()
        }
    }
}
