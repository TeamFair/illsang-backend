package com.teamfair.modulequest.application.port.out

import com.teamfair.modulequest.domain.model.QuizAnswer

interface QuizAnswerPersistencePort {
    fun save(quizAnswer: QuizAnswer): QuizAnswer
    fun findById(id: Long): QuizAnswer?
    fun findAll(): List<QuizAnswer>
    fun findByQuizId(quizId: Long): List<QuizAnswer>
    fun deleteById(id: Long)
    fun existsById(id: Long): Boolean
} 