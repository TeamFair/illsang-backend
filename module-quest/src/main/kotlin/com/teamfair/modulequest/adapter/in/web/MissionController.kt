package com.teamfair.modulequest.adapter.`in`.web

import com.illsang.common.enums.ResponseMsg
import com.teamfair.modulequest.adapter.`in`.web.model.request.CreateMissionRequest
import com.teamfair.modulequest.adapter.`in`.web.model.request.UpdateMissionRequest
import com.teamfair.modulequest.adapter.`in`.web.model.response.MissionResponse
import com.teamfair.modulequest.application.command.CreateMissionCommand
import com.teamfair.modulequest.application.command.UpdateMissionCommand
import com.teamfair.modulequest.application.service.MissionService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/missions")
class MissionController(
    private val missionService: MissionService
) {

    @PostMapping
    fun createMission(@RequestBody request: CreateMissionRequest): ResponseEntity<MissionResponse> {
        val command = CreateMissionCommand(
            type = request.type,
            title = request.title,
            sortOrder = request.sortOrder,
            questId = request.questId
        )
        val mission = missionService.createMission(command)
        return ResponseEntity.status(HttpStatus.CREATED).body(MissionResponse.from(mission))
    }

    @GetMapping("/{id}")
    fun getMission(@PathVariable id: Long): ResponseEntity<MissionResponse> {
        val mission = missionService.getMissionById(id)
        return if (mission != null) {
            ResponseEntity.ok(MissionResponse.from(mission))
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @GetMapping
    fun getAllMissions(): ResponseEntity<List<MissionResponse>> {
        val missions = missionService.getAllMissions()
        return ResponseEntity.ok(missions.map { MissionResponse.from(it) })
    }

    @GetMapping("/quest/{questId}")
    fun getMissionsByQuestId(@PathVariable questId: Long): ResponseEntity<List<MissionResponse>> {
        val missions = missionService.getMissionsByQuestId(questId)
        return ResponseEntity.ok(missions.map { MissionResponse.from(it) })
    }

    @PutMapping("/{id}")
    fun updateMission(
        @PathVariable id: Long,
        @RequestBody request: UpdateMissionRequest
    ): ResponseEntity<MissionResponse> {
        val command = UpdateMissionCommand(
            id = id,
            type = request.type,
            title = request.title,
            sortOrder = request.sortOrder
        )
        val mission = missionService.updateMission(command)
        return if (mission != null) {
            ResponseEntity.ok(MissionResponse.from(mission))
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/{id}")
    fun deleteMission(@PathVariable id: Long): ResponseEntity<ResponseMsg> {
        val deleted = missionService.deleteMission(id)
        return if (deleted) {
            ResponseEntity.ok(ResponseMsg.SUCCESS)
        } else {
            ResponseEntity.notFound().build()
        }
    }
} 