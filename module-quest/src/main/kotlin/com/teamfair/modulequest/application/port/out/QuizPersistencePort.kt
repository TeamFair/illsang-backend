package com.teamfair.modulequest.application.port.out

import com.teamfair.modulequest.domain.model.QuizModel

interface QuizPersistencePort {
    fun save(quizModel: QuizModel): QuizModel
    fun findById(id: Long): QuizModel?
    fun findAll(): List<QuizModel>
    fun findByMissionId(missionId: Long): List<QuizModel>
    fun deleteById(id: Long)
    fun existsById(id: Long): Boolean
} 