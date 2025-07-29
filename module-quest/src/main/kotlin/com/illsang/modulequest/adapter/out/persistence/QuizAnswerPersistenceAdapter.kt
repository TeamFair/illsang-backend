package com.illsang.modulequest.adapter.out.persistence

import com.illsang.modulequest.adapter.out.persistence.repository.QuizAnswerRepository
import com.illsang.modulequest.adapter.out.persistence.repository.QuizRepository
import com.illsang.modulequest.application.port.out.QuizAnswerPersistencePort
import com.illsang.modulequest.domain.mapper.QuizAnswerMapper
import com.illsang.modulequest.domain.model.QuizAnswerModel
import org.springframework.stereotype.Component

@Component
class QuizAnswerPersistenceAdapter(
    private val quizAnswerRepository: QuizAnswerRepository,
    private val quizRepository: QuizRepository
) : QuizAnswerPersistencePort {

    override fun save(quizAnswerModel: QuizAnswerModel): QuizAnswerModel {
        val quizEntity = quizRepository.findById(quizAnswerModel.quizId)
            .orElseThrow { IllegalArgumentException("Quiz not found with id: ${quizAnswerModel.quizId}") }

        val entity = QuizAnswerMapper.toEntity(quizAnswerModel, quizEntity)
        val savedEntity = quizAnswerRepository.save(entity)
        return QuizAnswerMapper.toModel(savedEntity)
    }

    override fun findById(id: Long): QuizAnswerModel? {
        return quizAnswerRepository.findById(id)
            .map { QuizAnswerMapper.toModel(it) }
            .orElse(null)
    }

    override fun findAll(): List<QuizAnswerModel> {
        return quizAnswerRepository.findAll()
            .map { QuizAnswerMapper.toModel(it) }
    }

    override fun findByQuizId(quizId: Long): List<QuizAnswerModel> {
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
