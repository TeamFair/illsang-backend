package com.illsang.quest.dto.request

import com.illsang.quest.domain.entity.MissionEntity
import com.illsang.quest.domain.entity.QuizEntity

data class QuizCreateRequest(
    val missionId: Long,
    val question: String,
    val hint: String? = null,
    val sortOrder: Int = 0,
    val answers: List<QuizAnswerCreateWithQuizRequest>,
) {

    fun toEntity(mission: MissionEntity): QuizEntity {
        val quiz = QuizEntity(
            question = this.question,
            hint = this.hint,
            sortOrder = this.sortOrder,
            mission = mission,
        )
        quiz.addAnswer(this.answers.map { it.toEntity(quiz) })

        return quiz
    }

}

data class QuizCreateWithMissionRequest(
    val question: String,
    val hint: String? = null,
    val sortOrder: Int = 0,
    val answers: List<QuizAnswerCreateWithQuizRequest>,
) {

    fun toEntity(mission: MissionEntity): QuizEntity {
        val quiz = QuizEntity(
            question = this.question,
            hint = this.hint,
            sortOrder = this.sortOrder,
            mission = mission,
        )
        quiz.addAnswer(this.answers.map { it.toEntity(quiz) })

        return quiz
    }

}

data class QuizUpdateRequest(
    val question: String,
    val hint: String? = null,
    val sortOrder: Int = 0,
)
