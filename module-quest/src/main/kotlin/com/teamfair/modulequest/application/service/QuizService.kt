package com.teamfair.modulequest.application.service

import com.teamfair.modulequest.adapter.out.persistence.entity.MissionEntity
import com.teamfair.modulequest.adapter.out.persistence.entity.QuizEntity
import com.teamfair.modulequest.adapter.out.persistence.repository.MissionRepository
import com.teamfair.modulequest.adapter.out.persistence.repository.QuizAnswerRepository
import com.teamfair.modulequest.adapter.out.persistence.repository.QuizRepository
import com.teamfair.modulequest.application.port.`in`.QuizUseCase
import com.teamfair.modulequest.domain.mapper.QuizAnswerMapper
import com.teamfair.modulequest.domain.mapper.QuizMapper
import com.teamfair.modulequest.domain.model.Quiz
import com.teamfair.modulequest.domain.model.QuizAnswer
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import jakarta.persistence.EntityNotFoundException

@Service
class QuizService(
    private val quizRepository: QuizRepository,
    private val quizAnswerRepository: QuizAnswerRepository,
    private val missionRepository: MissionRepository
) : QuizUseCase {

    // Quiz CRUD operations

    @Transactional
    override fun createQuiz(quiz: Quiz, missionId: Long): Quiz {
        val mission = missionRepository.findById(missionId)
            .orElseThrow { EntityNotFoundException("Mission not found with id: $missionId") }

        val quizEntity = QuizMapper.toEntity(quiz, mission)
        val savedEntity = quizRepository.save(quizEntity)

        return QuizMapper.toModel(savedEntity)
    }

    @Transactional(readOnly = true)
    override fun getQuiz(id: Long): Quiz {
        val quizEntity = quizRepository.findById(id)
            .orElseThrow { EntityNotFoundException("Quiz not found with id: $id") }

        return QuizMapper.toModel(quizEntity)
    }

    @Transactional(readOnly = true)
    override fun getAllQuizzes(): List<Quiz> {
        return quizRepository.findAll().map { QuizMapper.toModel(it) }
    }

    @Transactional(readOnly = true)
    override fun getQuizzesByMissionId(missionId: Long): List<Quiz> {
        val mission = missionRepository.findById(missionId)
            .orElseThrow { EntityNotFoundException("Mission not found with id: $missionId") }

        val quizEntities = quizRepository.findByMission(mission)
        return quizEntities.map { QuizMapper.toModel(it) }
    }

    @Transactional
    override fun updateQuiz(id: Long, quiz: Quiz): Quiz {
        val existingQuizEntity = quizRepository.findById(id)
            .orElseThrow { EntityNotFoundException("Quiz not found with id: $id") }

        // Update fields
        existingQuizEntity.question = quiz.question
        existingQuizEntity.hint = quiz.hint
        existingQuizEntity.sortOrder = quiz.sortOrder

        // Save the updated entity
        val updatedEntity = quizRepository.save(existingQuizEntity)

        return QuizMapper.toModel(updatedEntity)
    }

    @Transactional
    override fun deleteQuiz(id: Long) {
        if (!quizRepository.existsById(id)) {
            throw EntityNotFoundException("Quiz not found with id: $id")
        }

        quizRepository.deleteById(id)
    }

    // QuizAnswer CRUD operations

    @Transactional
    override fun createQuizAnswer(quizAnswer: QuizAnswer, quizId: Long): QuizAnswer {
        val quizEntity = quizRepository.findById(quizId)
            .orElseThrow { EntityNotFoundException("Quiz not found with id: $quizId") }

        val quizAnswerEntity = QuizAnswerMapper.toEntity(quizAnswer, quizEntity)
        quizEntity.addAnswer(quizAnswerEntity)

        quizRepository.save(quizEntity)

        return QuizAnswerMapper.toModel(quizAnswerEntity)
    }

    @Transactional(readOnly = true)
    override fun getQuizAnswer(id: Long): QuizAnswer {
        val quizAnswerEntity = quizAnswerRepository.findById(id)
            .orElseThrow { EntityNotFoundException("QuizAnswer not found with id: $id") }

        return QuizAnswerMapper.toModel(quizAnswerEntity)
    }

    @Transactional(readOnly = true)
    override fun getQuizAnswersByQuizId(quizId: Long): List<QuizAnswer> {
        val quizEntity = quizRepository.findById(quizId)
            .orElseThrow { EntityNotFoundException("Quiz not found with id: $quizId") }

        return quizEntity.answers.map { QuizAnswerMapper.toModel(it) }
    }

    @Transactional
    override fun updateQuizAnswer(id: Long, quizAnswer: QuizAnswer): QuizAnswer {
        val existingAnswerEntity = quizAnswerRepository.findById(id)
            .orElseThrow { EntityNotFoundException("QuizAnswer not found with id: $id") }

        // Update fields
        existingAnswerEntity.answer = quizAnswer.answer
        existingAnswerEntity.sortOrder = quizAnswer.sortOrder

        // Save the updated entity
        val updatedEntity = quizAnswerRepository.save(existingAnswerEntity)

        return QuizAnswerMapper.toModel(updatedEntity)
    }

    @Transactional
    override fun deleteQuizAnswer(id: Long) {
        if (!quizAnswerRepository.existsById(id)) {
            throw EntityNotFoundException("QuizAnswer not found with id: $id")
        }

        quizAnswerRepository.deleteById(id)
    }
}
