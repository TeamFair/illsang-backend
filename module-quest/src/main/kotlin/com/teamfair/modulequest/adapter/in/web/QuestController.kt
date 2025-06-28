package com.teamfair.modulequest.adapter.`in`.web

import com.illsang.common.enums.ResponseMsg
import com.teamfair.modulequest.adapter.`in`.web.model.request.CreateQuestRequest
import com.teamfair.modulequest.adapter.`in`.web.model.request.UpdateQuestRequest
import com.teamfair.modulequest.adapter.`in`.web.model.response.QuestResponse
import com.teamfair.modulequest.application.command.CreateQuestCommand
import com.teamfair.modulequest.application.command.UpdateQuestCommand
import com.teamfair.modulequest.application.service.QuestService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/quests")
class QuestController(
    private val questService: QuestService
) {

    @PostMapping
    fun createQuest(@RequestBody request: CreateQuestRequest): ResponseEntity<QuestResponse> {
        val command = CreateQuestCommand(
            imageId = request.imageId,
            writerName = request.writerName,
            mainImageId = request.mainImageId,
            popularYn = request.popularYn,
            type = request.type,
            repeatFrequency = request.repeatFrequency,
            sortOrder = request.sortOrder
        )
        val quest = questService.createQuest(command)
        return ResponseEntity.status(HttpStatus.CREATED).body(QuestResponse.from(quest))
    }

    @GetMapping("/{id}")
    fun getQuest(@PathVariable id: Long): ResponseEntity<QuestResponse> {
        val quest = questService.getQuestById(id)
        return if (quest != null) {
            ResponseEntity.ok(QuestResponse.from(quest))
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @GetMapping
    fun getAllQuests(): ResponseEntity<List<QuestResponse>> {
        val quests = questService.getAllQuests()
        return ResponseEntity.ok(quests.map { QuestResponse.from(it) })
    }

    @PutMapping("/{id}")
    fun updateQuest(
        @PathVariable id: Long,
        @RequestBody request: UpdateQuestRequest
    ): ResponseEntity<QuestResponse> {
        val command = UpdateQuestCommand(
            id = id,
            imageId = request.imageId,
            writerName = request.writerName,
            mainImageId = request.mainImageId,
            popularYn = request.popularYn,
            type = request.type,
            repeatFrequency = request.repeatFrequency,
            sortOrder = request.sortOrder
        )
        val quest = questService.updateQuest(command)
        return if (quest != null) {
            ResponseEntity.ok(QuestResponse.from(quest))
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/{id}")
    fun deleteQuest(@PathVariable id: Long): ResponseEntity<ResponseMsg> {
        val deleted = questService.deleteQuest(id)
        return if (deleted) {
            ResponseEntity.ok(ResponseMsg.SUCCESS)
        } else {
            ResponseEntity.notFound().build()
        }
    }
} 