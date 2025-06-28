package com.teamfair.modulequest.application.port.`in`

import com.teamfair.modulequest.domain.model.QuizModel
import com.teamfair.modulequest.domain.model.QuizAnswerModel

interface QuizUseCase {
    // Quiz CRUD operations
    fun createQuiz(quizModel: QuizModel, missionId: Long): QuizModel
    fun getQuiz(id: Long): QuizModel
    fun getAllQuizzes(): List<QuizModel>
    fun getQuizzesByMissionId(missionId: Long): List<QuizModel>
    fun updateQuiz(id: Long, quizModel: QuizModel): QuizModel
    fun deleteQuiz(id: Long)

    // QuizAnswer CRUD operations
    fun createQuizAnswer(quizAnswerModel: QuizAnswerModel, quizId: Long): QuizAnswerModel
    fun getQuizAnswer(id: Long): QuizAnswerModel
    fun getQuizAnswersByQuizId(quizId: Long): List<QuizAnswerModel>
    fun updateQuizAnswer(id: Long, quizAnswerModel: QuizAnswerModel): QuizAnswerModel
    fun deleteQuizAnswer(id: Long)
}
