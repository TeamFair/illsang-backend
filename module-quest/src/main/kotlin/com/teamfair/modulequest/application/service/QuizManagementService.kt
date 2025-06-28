package com.teamfair.modulequest.application.service

import com.teamfair.modulequest.application.command.CreateQuizCommand
import com.teamfair.modulequest.application.command.UpdateQuizCommand
import com.teamfair.modulequest.application.port.out.QuizPersistencePort
import com.teamfair.modulequest.domain.mapper.QuizMapper
import com.teamfair.modulequest.domain.model.Quiz
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class QuizManagementService(
    private val quizPersistencePort: QuizPersistencePort
) {

    @Transactional
    fun createQuiz(command: CreateQuizCommand): Quiz {
        val quizModel = QuizMapper.toModel(command)
        return quizPersistencePort.save(quizModel)
    }

    fun getQuizById(id: Long): Quiz? {
        return quizPersistencePort.findById(id)
    }

    fun getAllQuizzes(): List<Quiz> {
        return quizPersistencePort.findAll()
    }

    fun getQuizzesByMissionId(missionId: Long): List<Quiz> {
        return quizPersistencePort.findByMissionId(missionId)
    }

    @Transactional
    fun updateQuiz(command: UpdateQuizCommand): Quiz? {
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