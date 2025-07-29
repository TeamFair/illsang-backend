package com.illsang.modulequest.application.port.out

import com.illsang.modulequest.domain.model.QuizModel

interface QuizPersistencePort {
    fun save(quizModel: QuizModel): QuizModel
    fun findById(id: Long): QuizModel?
    fun findAll(): List<QuizModel>
    fun findByMissionId(missionId: Long): List<QuizModel>
    fun deleteById(id: Long)
    fun existsById(id: Long): Boolean
}
