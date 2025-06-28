package com.teamfair.modulequest.adapter.out.persistence

import com.teamfair.modulequest.adapter.out.persistence.repository.QuestRepository
import com.teamfair.modulequest.application.port.out.QuestPersistencePort
import com.teamfair.modulequest.domain.mapper.QuestMapper
import com.teamfair.modulequest.domain.model.QuestModel
import org.springframework.stereotype.Component

@Component
class QuestPersistenceAdapter(
    private val questRepository: QuestRepository,
) : QuestPersistencePort {
    
    /**
     * save quest
     */
    override fun save(questModel: QuestModel): QuestModel {
        val entity = QuestMapper.toEntity(questModel)
        val savedEntity = questRepository.save(entity)
        return QuestMapper.toModel(savedEntity)
    }
    
    /**
     * find quest by id
     */
    override fun findById(id: Long): QuestModel? {
        return questRepository.findById(id)
            .map { QuestMapper.toModel(it) }
            .orElse(null)
    }
    
    /**
     * find all quests
     */
    override fun findAll(): List<QuestModel> {
        return questRepository.findAll()
            .map { QuestMapper.toModel(it) }
    }
    
    /**
     * find quests by type
     */
    override fun findByType(type: String): List<QuestModel> {
        return questRepository.findByType(type)
            .map { QuestMapper.toModel(it) }
    }
    
    /**
     * find quests by popular yn
     */
    override fun findByPopularYn(popularYn: Boolean): List<QuestModel> {
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