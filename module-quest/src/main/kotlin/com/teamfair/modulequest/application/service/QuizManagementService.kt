package com.teamfair.modulequest.application.service

import com.teamfair.modulequest.application.command.CreateQuizCommand
import com.teamfair.modulequest.application.command.UpdateQuizCommand
import com.teamfair.modulequest.application.port.out.QuizPersistencePort
import com.teamfair.modulequest.domain.mapper.QuizMapper
import com.teamfair.modulequest.domain.model.QuizModel
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class QuizManagementService(
    private val quizPersistencePort: QuizPersistencePort
) {

    @Transactional
    fun createQuiz(command: CreateQuizCommand): QuizModel {
        val quizModel = QuizMapper.toModel(command)
        return quizPersistencePort.save(quizModel)
    }

    fun getQuizById(id: Long): QuizModel? {
        return quizPersistencePort.findById(id)
    }

    fun getAllQuizzes(): List<QuizModel> {
        return quizPersistencePort.findAll()
    }

    fun getQuizzesByMissionId(missionId: Long): List<QuizModel> {
        return quizPersistencePort.findByMissionId(missionId)
    }

    @Transactional
    fun updateQuiz(command: UpdateQuizCommand): QuizModel? {
        val existingQuiz = quizPersistencePort.findById(command.id) ?: return null
        val updatedQuiz = QuizMapper.toModel(command, existingQuiz)
        return quizPersistencePort.save(updatedQuiz)
    }

    @Transactional
    fun deleteQuiz(id: Long): Boolean {
        return if (quizPersistencePort.existsById(id)) {
            quizPersistencePort.deleteById(id)
            true
        } else {
            false
        }
    }
} 