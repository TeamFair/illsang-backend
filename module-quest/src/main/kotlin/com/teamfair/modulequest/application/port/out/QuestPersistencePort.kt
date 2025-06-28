package com.teamfair.modulequest.application.port.out

import com.teamfair.modulequest.domain.model.QuestModel

interface QuestPersistencePort {
    fun save(questModel: QuestModel): QuestModel
    fun findById(id: Long): QuestModel?
    fun findAll(): List<QuestModel>
    fun findByType(type: String): List<QuestModel>
    fun findByPopularYn(popularYn: Boolean): List<QuestModel>
    fun deleteById(id: Long)
    fun existsById(id: Long): Boolean
} 