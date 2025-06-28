package com.teamfair.modulequest.adapter.out.persistence

import com.teamfair.modulequest.adapter.out.persistence.entity.QuizAnswerEntity
import com.teamfair.modulequest.adapter.out.persistence.entity.QuizEntity
import com.teamfair.modulequest.adapter.out.persistence.repository.QuizAnswerRepository
import com.teamfair.modulequest.adapter.out.persistence.repository.QuizRepository
import com.teamfair.modulequest.application.port.out.QuizAnswerPersistencePort
import com.teamfair.modulequest.domain.mapper.QuizAnswerMapper
import com.teamfair.modulequest.domain.model.QuizAnswer
import org.springframework.stereotype.Component

@Component
class QuizAnswerPersistenceAdapter(
    private val quizAnswerRepository: QuizAnswerRepository,
    private val quizRepository: QuizRepository
) : QuizAnswerPersistencePort {

    override fun save(quizAnswer: QuizAnswer): QuizAnswer {
        val quizEntity = quizRepository.findById(quizAnswer.quizId)
            .orElseThrow { IllegalArgumentException("Quiz not found with id: ${quizAnswer.quizId}") }
        
        val entity = QuizAnswerMapper.toEntity(quizAnswer, quizEntity)
        val savedEntity = quizAnswerRepository.save(entity)
        return QuizAnswerMapper.toModel(savedEntity)
    }

    override fun findById(id: Long): QuizAnswer? {
        return quizAnswerRepository.findById(id)
            .map { QuizAnswerMapper.toModel(it) }
            .orElse(null)
    }

    override fun findAll(): List<QuizAnswer> {
        return quizAnswerRepository.findAll()
            .map { QuizAnswerMapper.toModel(it) }
    }

    override fun findByQuizId(quizId: Long): List<QuizAnswer> {
        return quizAnswerRepository.findByQuizId(quizId)
            .map { QuizAnswerMapper.toModel(it) }
    }

    override fun deleteById(id: Long) {
        quizAnswerRepository.deleteById(id)
    }

    override fun existsById(id: Long): Boolean {
        return quizAnswerRepository.existsById(id)
    }
} 