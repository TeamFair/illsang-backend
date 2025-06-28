package com.teamfair.modulequest.application.service

import com.teamfair.modulequest.application.command.CreateQuizAnswerCommand
import com.teamfair.modulequest.application.command.UpdateQuizAnswerCommand
import com.teamfair.modulequest.application.port.out.QuizAnswerPersistencePort
import com.teamfair.modulequest.domain.mapper.QuizAnswerMapper
import com.teamfair.modulequest.domain.model.QuizAnswer
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class QuizAnswerService(
    private val quizAnswerPersistencePort: QuizAnswerPersistencePort
) {

    @Transactional
    fun createQuizAnswer(command: CreateQuizAnswerCommand): QuizAnswer {
        val quizAnswerModel = QuizAnswerMapper.toModel(command)
        return quizAnswerPersistencePort.save(quizAnswerModel)
    }

    fun getQuizAnswerById(id: Long): QuizAnswer? {
        return quizAnswerPersistencePort.findById(id)
    }

    fun getAllQuizAnswers(): List<QuizAnswer> {
        return quizAnswerPersistencePort.findAll()
    }

    fun getQuizAnswersByQuizId(quizId: Long): List<QuizAnswer> {
        return quizAnswerPersistencePort.findByQuizId(quizId)
    }

    @Transactional
    fun updateQuizAnswer(command: UpdateQuizAnswerCommand): QuizAnswer? {
        val existingQuizAnswer = quizAnswerPersistencePort.findById(command.id) ?: return null
        val updatedQuizAnswer = QuizAnswerMapper.toModel(command, existingQuizAnswer)
        return quizAnswerPersistencePort.save(updatedQuizAnswer)
    }

    @Transactional
    fun deleteQuizAnswer(id: Long): Boolean {
        return if (quizAnswerPersistencePort.existsById(id)) {
            quizAnswerPersistencePort.deleteById(id)
            true
        } else {
            false
        }
    }
} 