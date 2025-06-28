package com.teamfair.modulequest.adapter.out.persistence

import com.teamfair.modulequest.adapter.out.persistence.repository.QuestRepository
import com.teamfair.modulequest.application.port.out.QuestPersistencePort
import com.teamfair.modulequest.domain.mapper.QuestMapper
import com.teamfair.modulequest.domain.model.Quest
import org.springframework.stereotype.Component

@Component
class QuestPersistenceAdapter(
    private val questRepository: QuestRepository,
) : QuestPersistencePort {
    
    /**
     * save quest
     */
    override fun save(quest: Quest): Quest {
        val entity = QuestMapper.toEntity(quest)
        val savedEntity = questRepository.save(entity)
        return QuestMapper.toModel(savedEntity)
    }
    
    /**
     * find quest by id
     */
    override fun findById(id: Long): Quest? {
        return questRepository.findById(id)
            .map { QuestMapper.toModel(it) }
            .orElse(null)
    }
    
    /**
     * find all quests
     */
    override fun findAll(): List<Quest> {
        return questRepository.findAll()
            .map { QuestMapper.toModel(it) }
    }
    
    /**
     * find quests by type
     */
    override fun findByType(type: String): List<Quest> {
        return questRepository.findByType(type)
            .map { QuestMapper.toModel(it) }
    }
    
    /**
     * find quests by popular yn
     */
    override fun findByPopularYn(popularYn: Boolean): List<Quest> {
        return questRepository.findByPopularYn(popularYn)
            .map { QuestMapper.toModel(it) }
    }
    
    /**
     * delete quest by id
     */
    override fun deleteById(id: Long) {
        questRepository.deleteById(id)
    }
    
    /**
     * check quest exists by id
     */
    override fun existsById(id: Long): Boolean {
        return questRepository.existsById(id)
    }
} 