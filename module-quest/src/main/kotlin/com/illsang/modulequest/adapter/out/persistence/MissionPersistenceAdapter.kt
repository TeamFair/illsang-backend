package com.illsang.modulequest.adapter.out.persistence

import com.illsang.modulequest.adapter.out.persistence.repository.MissionRepository
import com.illsang.modulequest.adapter.out.persistence.repository.QuestRepository
import com.illsang.modulequest.application.port.out.MissionPersistencePort
import com.illsang.modulequest.domain.mapper.MissionMapper
import com.illsang.modulequest.domain.model.MissionModel
import org.springframework.stereotype.Component

@Component
class MissionPersistenceAdapter(
    private val missionRepository: MissionRepository,
    private val questRepository: QuestRepository
) : MissionPersistencePort {

    /**
     * save mission
     */
    override fun save(missionModel: MissionModel): MissionModel {
        val questEntity = questRepository.findById(missionModel.questId)
            .orElseThrow { IllegalArgumentException("Quest not found with id: ${missionModel.questId}") }

        val entity = MissionMapper.toEntity(missionModel, questEntity)
        val savedEntity = missionRepository.save(entity)
        return MissionMapper.toModel(savedEntity)
    }

    /**
     * find mission by id
     */
    override fun findById(id: Long): MissionModel? {
        return missionRepository.findById(id)
            .map { MissionMapper.toModel(it) }
            .orElse(null)
    }

    /**
     * find missions by quest id
     */
    override fun findByQuestId(questId: Long): List<MissionModel> {
        return missionRepository.findByQuestId(questId)
            .map { MissionMapper.toModel(it) }
    }

    /**
     * find all missions
     */
    override fun findAll(): List<MissionModel> {
        return missionRepository.findAll()
            .map { MissionMapper.toModel(it) }
    }

    /**
     * delete mission by id
     */
    override fun deleteById(id: Long) {
        missionRepository.deleteById(id)
    }

    /**
     * check mission exists by id
     */
    override fun existsById(id: Long): Boolean {
        return missionRepository.existsById(id)
    }
}
