package com.teamfair.illsang.quest.controller

import com.teamfair.illsang.quest.dto.QuestResponse
import com.teamfair.illsang.quest.dto.QuestSaveRequest
import com.teamfair.illsang.quest.service.QuestService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/admin/v1/quests")
class QuestController(
    val questService: QuestService,
) {

    @PostMapping
    fun save(
        @RequestBody questSaveRequest: QuestSaveRequest
    ): ResponseEntity<QuestResponse> {

        return ResponseEntity.ok().body(QuestResponse(id = 1))
    }

}
