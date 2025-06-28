package com.teamfair.modulequest.application.port.out

import com.teamfair.modulequest.domain.model.Quiz

interface QuizPersistencePort {
    fun save(quiz: Quiz): Quiz
    fun findById(id: Long): Quiz?
    fun findAll(): List<Quiz>
    fun findByMissionId(missionId: Long): List<Quiz>
    fun deleteById(id: Long)
    fun existsById(id: Long): Boolean
} 