package com.illsang.quest.service

import com.illsang.quest.domain.entity.QuizEntity
import com.illsang.quest.domain.model.QuizModel
import com.illsang.quest.dto.request.QuizCreateRequest
import com.illsang.quest.dto.request.QuizUpdateRequest
import com.illsang.quest.repository.QuizRepository
import jakarta.persistence.Id
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class QuizService(
    private val quizRepository: QuizRepository,
    private val missionService: MissionService,
    ) {

    @Transactional
    fun createQuiz(request: QuizCreateRequest): QuizModel {
        val mission = this.missionService.findById(request.missionId)

        val quiz = request.toEntity(mission)
        this.quizRepository.save(quiz)

        return QuizModel.from(quiz)
    }

    fun getQuizById(id: Long): QuizModel {
        val quiz = this.findById(id)

        return QuizModel.from(quiz)
    }

    @Transactional
    fun updateQuiz(id: Long, request: QuizUpdateRequest): QuizModel {
        val quiz = this.findById(id)

        quiz.update(request)

        return QuizModel.from(quiz)
    }

    @Transactional
    fun deleteQuiz(id: Long) {
        val quiz = this.findById(id)

        this.missionService.deleteQuiz(quiz.mission.id!!, quiz.id!!)
    }

    fun findById(id: Long): QuizEntity = this.quizRepository.findByIdOrNull(id)
        ?: throw IllegalArgumentException("Quiz not found with id: $id")

    @Transactional
    fun deleteQuizAnswer(id: Long, quizAnswerId: Long) {
        val quiz = this.findById(id)

        quiz.deleteQuizAnswer(quizAnswerId)
    }

}
