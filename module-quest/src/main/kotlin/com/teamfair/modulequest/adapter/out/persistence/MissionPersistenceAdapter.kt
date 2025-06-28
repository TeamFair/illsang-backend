package com.teamfair.modulequest.adapter.out.persistence

import com.teamfair.modulequest.adapter.out.persistence.repository.MissionRepository
import com.teamfair.modulequest.application.port.out.MissionPersistencePort
import com.teamfair.modulequest.domain.mapper.MissionMapper
import com.teamfair.modulequest.domain.model.Mission
import org.springframework.stereotype.Component

@Component
class MissionPersistenceAdapter(
    private val missionRepository: MissionRepository,
) : MissionPersistencePort {
    
    /**
     * save mission
     */
    override fun save(mission: Mission): Mission {
        // Mission은 Quest와 연관관계가 있으므로 Quest를 찾아서 매핑해야 함
        // 실제 구현에서는 Quest ID를 받아서 처리하거나 다른 방식으로 처리해야 함
        throw NotImplementedError("Mission save requires Quest context")
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