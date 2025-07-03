package com.illsang.modulequest.application.service

import com.illsang.modulequest.application.command.CreateQuizAnswerCommand
import com.illsang.modulequest.application.command.UpdateQuizAnswerCommand
import com.illsang.modulequest.application.port.out.QuizAnswerPersistencePort
import com.illsang.modulequest.domain.mapper.QuizAnswerMapper
import com.illsang.modulequest.domain.model.QuizAnswerModel
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class QuizAnswerService(
    private val quizAnswerPersistencePort: QuizAnswerPersistencePort
) {

    @Transactional
    fun createQuizAnswer(command: CreateQuizAnswerCommand): QuizAnswerModel {
        val quizAnswerModel = QuizAnswerMapper.toModel(command)
        return quizAnswerPersistencePort.save(quizAnswerModel)
    }

    fun getQuizAnswerById(id: Long): QuizAnswerModel? {
        return quizAnswerPersistencePort.findById(id)
    }

    fun getAllQuizAnswers(): List<QuizAnswerModel> {
        return quizAnswerPersistencePort.findAll()
    }

    fun getQuizAnswersByQuizId(quizId: Long): List<QuizAnswerModel> {
        return quizAnswerPersistencePort.findByQuizId(quizId)
    }

    @Transactional
    fun updateQuizAnswer(command: UpdateQuizAnswerCommand): QuizAnswerModel? {
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
