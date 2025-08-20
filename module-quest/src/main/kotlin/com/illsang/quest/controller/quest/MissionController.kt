package com.illsang.quest.controller.quest

import com.illsang.common.enums.ResponseMsg
import com.illsang.quest.dto.request.quest.MissionCreateRequest
import com.illsang.quest.dto.request.quest.MissionUpdateRequest
import com.illsang.quest.dto.response.quest.MissionResponse
import com.illsang.quest.service.quest.MissionService
import com.illsang.quest.service.user.MissionHistoryService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/mission")
@Tag(name = "Mission", description = "미션")
class MissionController(
    private val missionService: MissionService,
    private val missionHistoryService: MissionHistoryService,
) {

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(operationId = "MIS001", summary= "미션 생성")
    fun createMission(
        @RequestBody request: MissionCreateRequest,
    ): ResponseEntity<MissionResponse> {
        val mission = this.missionService.createMission(request)

        return ResponseEntity.status(HttpStatus.CREATED).body(
            MissionResponse.from(mission)
        )
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(operationId = "MIS002", summary= "미션 단일 조회")
    fun getMission(
        @PathVariable id: Long,
    ): ResponseEntity<MissionResponse> {
        val mission = this.missionService.getMissionById(id)

        return ResponseEntity.ok(MissionResponse.from(mission))
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(operationId = "MIS003", summary= "미션 수정")
    fun updateMission(
        @PathVariable id: Long,
        @RequestBody request: MissionUpdateRequest,
    ): ResponseEntity<MissionResponse> {
        val mission = this.missionService.updateMission(id, request)

        return ResponseEntity.ok(
            MissionResponse.from(mission)
        )
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(operationId = "MIS004", summary= "미션 삭제")
    fun deleteMission(
        @PathVariable id: Long,
    ): ResponseEntity<ResponseMsg> {
        this.missionService.deleteMission(id)

        return ResponseEntity.ok().build()
    }

}
