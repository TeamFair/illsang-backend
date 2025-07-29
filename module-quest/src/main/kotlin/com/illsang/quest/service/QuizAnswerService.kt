package com.illsang.quest.service

import com.illsang.quest.domain.entity.QuizAnswerEntity
import com.illsang.quest.domain.entity.QuizEntity
import com.illsang.quest.domain.model.QuizAnswerModel
import com.illsang.quest.dto.request.QuizAnswerCreateRequest
import com.illsang.quest.dto.request.QuizAnswerUpdateRequest
import com.illsang.quest.repository.QuizAnswerRepository
import jakarta.persistence.EntityManager
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class QuizAnswerService(
    private val quizAnswerRepository: QuizAnswerRepository,
    private val quizService: QuizService,
) {

    @Transactional
    fun createQuizAnswer(request: QuizAnswerCreateRequest): QuizAnswerModel {
        val quiz = this.quizService.findById(request.quizId)

        val quizAnswer = request.toEntity(quiz)
        this.quizAnswerRepository.save(quizAnswer)

        return QuizAnswerModel.from(quizAnswer)
    }

    fun getQuizAnswerById(id: Long): QuizAnswerModel {
        val quizAnswer = this.findById(id)

        return QuizAnswerModel.from(quizAnswer)
    }

    @Transactional
    fun updateQuizAnswer(id: Long, request: QuizAnswerUpdateRequest): QuizAnswerModel {
        val quizAnswer = this.findById(id)

        quizAnswer.update(request)

        return QuizAnswerModel.from(quizAnswer)
    }

    @Transactional
    fun deleteQuizAnswer(id: Long) {
        val quizAnswer = this.findById(id)

        this.quizService.deleteQuizAnswer(quizAnswer.quiz.id!!, quizAnswer.id!!)

        this.quizAnswerRepository.delete(quizAnswer)
    }

    private fun findById(id: Long): QuizAnswerEntity = this.quizAnswerRepository.findByIdOrNull(id)
        ?: throw IllegalArgumentException("QuizAnswer not found with id: $id")

}
