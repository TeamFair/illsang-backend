package com.illsang.quest.dto.request.quest

import com.illsang.quest.domain.entity.quest.QuizAnswerEntity
import com.illsang.quest.domain.entity.quest.QuizEntity

data class QuizAnswerCreateRequest(
    val quizId: Long,
    val answer: String,
    val sortOrder: Int = 0,
) {
    fun toEntity(quiz: QuizEntity): QuizAnswerEntity {
        return QuizAnswerEntity(
            answer = this.answer,
            sortOrder = this.sortOrder,
            quiz = quiz,
        )
    }
}

data class QuizAnswerCreateWithQuizRequest(
    val answer: String,
    val sortOrder: Int = 0,
) {
    fun toEntity(quiz: QuizEntity): QuizAnswerEntity {
        return QuizAnswerEntity(
            answer = this.answer,
            sortOrder = this.sortOrder,
            quiz = quiz,
        )
    }
}

data class QuizAnswerUpdateRequest(
    val answer: String,
    val sortOrder: Int = 0,
)
