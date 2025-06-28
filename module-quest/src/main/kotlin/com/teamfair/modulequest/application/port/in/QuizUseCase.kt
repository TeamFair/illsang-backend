package com.teamfair.modulequest.application.port.`in`

import com.teamfair.modulequest.domain.model.Quiz
import com.teamfair.modulequest.domain.model.QuizAnswer

interface QuizUseCase {
    // Quiz CRUD operations
    fun createQuiz(quiz: Quiz, missionId: Long): Quiz
    fun getQuiz(id: Long): Quiz
    fun getAllQuizzes(): List<Quiz>
    fun getQuizzesByMissionId(missionId: Long): List<Quiz>
    fun updateQuiz(id: Long, quiz: Quiz): Quiz
    fun deleteQuiz(id: Long)

    // QuizAnswer CRUD operations
    fun createQuizAnswer(quizAnswer: QuizAnswer, quizId: Long): QuizAnswer
    fun getQuizAnswer(id: Long): QuizAnswer
    fun getQuizAnswersByQuizId(quizId: Long): List<QuizAnswer>
    fun updateQuizAnswer(id: Long, quizAnswer: QuizAnswer): QuizAnswer
    fun deleteQuizAnswer(id: Long)
}
