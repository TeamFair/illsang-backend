package com.teamfair.modulequest.application.port.out

import com.teamfair.modulequest.domain.model.Quest

interface QuestPersistencePort {
    fun save(quest: Quest): Quest
    fun findById(id: Long): Quest?
    fun findAll(): List<Quest>
    fun findByType(type: String): List<Quest>
    fun findByPopularYn(popularYn: Boolean): List<Quest>
    fun deleteById(id: Long)
    fun existsById(id: Long): Boolean
} 