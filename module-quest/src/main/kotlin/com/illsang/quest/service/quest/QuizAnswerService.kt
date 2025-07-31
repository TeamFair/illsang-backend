package com.illsang.quest.service.quest

import com.illsang.quest.domain.entity.quest.QuizAnswerEntity
import com.illsang.quest.domain.model.quset.QuizAnswerModel
import com.illsang.quest.dto.request.quest.QuizAnswerCreateRequest
import com.illsang.quest.dto.request.quest.QuizAnswerUpdateRequest
import com.illsang.quest.repository.quest.QuizAnswerRepository
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
