package com.teamfair.modulequest.application.port.out

import com.teamfair.modulequest.domain.model.Mission

interface MissionPersistencePort {
    fun save(mission: Mission): Mission
    fun findById(id: Long): Mission?
    fun findByQuestId(questId: Long): List<Mission>
    fun findAll(): List<Mission>
    fun deleteById(id: Long)
    fun existsById(id: Long): Boolean
} 