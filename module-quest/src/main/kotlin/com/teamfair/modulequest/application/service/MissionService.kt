package com.teamfair.modulequest.application.service

import com.teamfair.modulequest.application.command.CreateMissionCommand
import com.teamfair.modulequest.application.command.UpdateMissionCommand
import com.teamfair.modulequest.application.port.out.MissionPersistencePort
import com.teamfair.modulequest.domain.mapper.MissionMapper
import com.teamfair.modulequest.domain.model.MissionModel
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class MissionService(
    private val missionPersistencePort: MissionPersistencePort
) {

    @Transactional
    fun createMission(command: CreateMissionCommand): MissionModel {
        val missionModel = MissionMapper.toModel(command)
        return missionPersistencePort.save(missionModel)
    }

    fun getMissionById(id: Long): MissionModel? {
        return missionPersistencePort.findById(id)
    }

    fun getAllMissions(): List<MissionModel> {
        return missionPersistencePort.findAll()
    }

    fun getMissionsByQuestId(questId: Long): List<MissionModel> {
        return missionPersistencePort.findByQuestId(questId)
    }

    @Transactional
    fun updateMission(command: UpdateMissionCommand): MissionModel? {
        val existingMission = missionPersistencePort.findById(command.id) ?: return null
        val updatedMission = MissionMapper.toModel(command, existingMission)
        return missionPersistencePort.save(updatedMission)
    }

    @Transactional
    fun deleteMission(id: Long): Boolean {
        return if (missionPersistencePort.existsById(id)) {
            missionPersistencePort.deleteById(id)
            true
        } else {
            false
        }
    }
} 