package com.illsang.quest.controller.quest

import com.illsang.quest.dto.request.quest.MissionLabelCreateRequest
import com.illsang.quest.dto.request.quest.MissionLabelUpdateRequest
import com.illsang.quest.dto.response.quest.MissionLabelResponse
import com.illsang.quest.service.quest.MissionLabelService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/mission/label")
@Tag(name = "Mission Label", description = "미션 라벨링")
class MissionLabelController(
    private val missionLabelService: MissionLabelService,
) {

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(operationId = "MLB001", summary= "미션 라벨 생성")
    fun createMissionLabel(
        @RequestBody request: MissionLabelCreateRequest,
    ): ResponseEntity<MissionLabelResponse> {
        val missionLabel = this.missionLabelService.createMissionLabel(request)

        return ResponseEntity.status(HttpStatus.CREATED).body(
            MissionLabelResponse.from(missionLabel)
        )
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(operationId = "MLB002", summary= "미션 라벨 단일 조회")
    fun getMissionLabel(@PathVariable id: Long): ResponseEntity<MissionLabelResponse> {
        val missionLabel = this.missionLabelService.getMissionLabelById(id)

        return ResponseEntity.ok(
            MissionLabelResponse.from(missionLabel)
        )
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(operationId = "MLB003", summary= "미션 라벨 수정")
    fun updateMissionLabel(
        @PathVariable id: Long,
        @RequestBody request: MissionLabelUpdateRequest,
    ): ResponseEntity<MissionLabelResponse> {
        val missionLabel = this.missionLabelService.updateMissionLabel(id, request)

        return ResponseEntity.ok(
            MissionLabelResponse.from(missionLabel)
        )
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(operationId = "MLB004", summary= "미션 라벨 석재")
    fun deleteMissionLabel(
        @PathVariable id: Long,
    ): ResponseEntity<Void> {
        this.missionLabelService.deleteMissionLabel(id)

        return ResponseEntity.ok().build()
    }
}
