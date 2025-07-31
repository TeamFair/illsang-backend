package com.illsang.quest.service.quest

import com.illsang.quest.domain.entity.quest.MissionEntity
import com.illsang.quest.domain.model.quset.MissionModel
import com.illsang.quest.dto.request.quest.MissionCreateRequest
import com.illsang.quest.dto.request.quest.MissionUpdateRequest
import com.illsang.quest.repository.quest.MissionRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class MissionService(
    private val missionRepository: MissionRepository,
    private val questService: QuestService,
) {

    @Transactional
    fun createMission(request: MissionCreateRequest): MissionModel {
        val quest = this.questService.findById(request.questId)

        val mission = request.toEntity(quest)
        this.missionRepository.save(mission)

        return MissionModel.from(mission)
    }

    fun getMissionById(id: Long): MissionModel {
        val mission = this.findById(id)

        return MissionModel.from(mission)
    }

    @Transactional
    fun updateMission(id: Long, request: MissionUpdateRequest): MissionModel {
        val mission = this.findById(id)

        mission.update(request)

        return MissionModel.from(mission)
    }

    @Transactional
    fun deleteMission(id: Long) {
        val mission = this.findById(id)

        this.missionRepository.delete(mission)
    }

    fun findById(id: Long): MissionEntity = this.missionRepository.findByIdOrNull(id)
        ?: throw IllegalArgumentException("Mission not found with id: $id")

    @Transactional
    fun deleteQuiz(id: Long, quizId: Long) {
        val mission = this.findById(id)

        mission.deleteQuiz(quizId)
    }

}
