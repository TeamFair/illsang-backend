package com.teamfair.modulequest.adapter.out.persistence

import com.teamfair.modulequest.adapter.out.persistence.entity.MissionEntity
import com.teamfair.modulequest.adapter.out.persistence.entity.QuestEntity
import com.teamfair.modulequest.adapter.out.persistence.repository.MissionRepository
import com.teamfair.modulequest.adapter.out.persistence.repository.QuestRepository
import com.teamfair.modulequest.application.port.out.MissionPersistencePort
import com.teamfair.modulequest.domain.mapper.MissionMapper
import com.teamfair.modulequest.domain.model.Mission
import org.springframework.stereotype.Component

@Component
class MissionPersistenceAdapter(
    private val missionRepository: MissionRepository,
    private val questRepository: QuestRepository
) : MissionPersistencePort {
    
    /**
     * save mission
     */
    override fun save(mission: Mission): Mission {
        val questEntity = questRepository.findById(mission.questId)
            .orElseThrow { IllegalArgumentException("Quest not found with id: ${mission.questId}") }
        
        val entity = MissionMapper.toEntity(mission, questEntity)
        val savedEntity = missionRepository.save(entity)
        return MissionMapper.toModel(savedEntity)
    }
    
    /**
     * find mission by id
     */
    override fun findById(id: Long): Mission? {
        return missionRepository.findById(id)
            .map { MissionMapper.toModel(it) }
            .orElse(null)
    }
    
    /**
     * find missions by quest id
     */
    override fun findByQuestId(questId: Long): List<Mission> {
        return missionRepository.findByQuestId(questId)
            .map { MissionMapper.toModel(it) }
    }
    
    /**
     * find all missions
     */
    override fun findAll(): List<Mission> {
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