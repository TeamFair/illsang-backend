package com.illsang.modulequest.application.port.out

import com.illsang.modulequest.domain.model.QuizAnswerModel

interface QuizAnswerPersistencePort {
    fun save(quizAnswerModel: QuizAnswerModel): QuizAnswerModel
    fun findById(id: Long): QuizAnswerModel?
    fun findAll(): List<QuizAnswerModel>
    fun findByQuizId(quizId: Long): List<QuizAnswerModel>
    fun deleteById(id: Long)
    fun existsById(id: Long): Boolean
}
