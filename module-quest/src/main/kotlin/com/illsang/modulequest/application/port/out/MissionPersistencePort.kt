package com.illsang.modulequest.application.port.out

import com.illsang.modulequest.domain.model.MissionModel

interface MissionPersistencePort {
    fun save(missionModel: MissionModel): MissionModel
    fun findById(id: Long): MissionModel?
    fun findAll(): List<MissionModel>
    fun findByQuestId(questId: Long): List<MissionModel>
    fun deleteById(id: Long)
    fun existsById(id: Long): Boolean
}
