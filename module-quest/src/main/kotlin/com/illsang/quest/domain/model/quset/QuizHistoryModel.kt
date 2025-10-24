package com.illsang.quest.domain.model.quset

import com.illsang.quest.domain.entity.quest.QuizEntity
import com.illsang.quest.domain.entity.user.UserQuizHistoryEntity
import java.time.LocalDateTime

data class QuizHistoryModel(
    val id: Long?,
    val question: String?,
    val answers: List<QuizAnswerModel>?,
    val userAnswer: String?,
    val createdAt: LocalDateTime?
) {
    companion object{
        fun from(quiz: QuizEntity?, userQuiz: UserQuizHistoryEntity?): QuizHistoryModel{
            return QuizHistoryModel(
                id = quiz?.id,
                question = quiz?.question,
                answers = quiz?.answers?.map { QuizAnswerModel.from(it) },
                userAnswer = userQuiz?.answer,
                createdAt = userQuiz?.createdAt
            )
        }
    }
}