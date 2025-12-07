package com.illsang.quest.service.quest

import com.illsang.quest.domain.entity.quest.MissionLabelEntity
import com.illsang.quest.domain.model.quset.MissionLabelModel
import com.illsang.quest.dto.request.quest.MissionLabelCreateRequest
import com.illsang.quest.dto.request.quest.MissionLabelUpdateRequest
import com.illsang.quest.repository.quest.MissionLabelRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class MissionLabelService(
    private val missionLabelRepository: MissionLabelRepository,
    private val missionService: MissionService,
) {

    @Transactional
    fun createMissionLabel(request: MissionLabelCreateRequest): MissionLabelModel {
        val mission = this.missionService.findById(request.missionId)

        val missionLabel = request.toEntity(mission)
        this.missionLabelRepository.save(missionLabel)

        return MissionLabelModel.from(missionLabel)
    }

    fun getMissionLabelById(id: Long): MissionLabelModel {
        val missionLabel = this.findById(id)

        return MissionLabelModel.from(missionLabel)
    }

    @Transactional
    fun updateMissionLabel(id: Long, request: MissionLabelUpdateRequest): MissionLabelModel {
        val missionLabel = this.findById(id)

        missionLabel.update(request)

        return MissionLabelModel.from(missionLabel)
    }

    @Transactional
    fun deleteMissionLabel(id: Long) {
        val missionLabel = this.findById(id)

        this.missionLabelRepository.delete(missionLabel)
    }

    fun findById(id: Long): MissionLabelEntity = this.missionLabelRepository.findByIdOrNull(id)
        ?: throw IllegalArgumentException("Mission Label not found with id: $id")

}
