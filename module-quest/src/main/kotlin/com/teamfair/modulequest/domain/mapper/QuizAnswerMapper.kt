package com.teamfair.modulequest.domain.mapper

import com.teamfair.modulequest.adapter.out.persistence.entity.QuizAnswerEntity
import com.teamfair.modulequest.adapter.out.persistence.entity.QuizEntity
import com.teamfair.modulequest.application.command.CreateQuizAnswerCommand
import com.teamfair.modulequest.application.command.UpdateQuizAnswerCommand
import com.teamfair.modulequest.domain.model.QuizAnswer

object QuizAnswerMapper {
    fun toModel(entity: QuizAnswerEntity): QuizAnswer {
        return QuizAnswer(
            id = entity.id,
            answer = entity.answer,
            sortOrder = entity.sortOrder,
            quizId = entity.quiz.id!!,
            createdBy = entity.createdBy,
            createdAt = entity.createdAt,
            updatedBy = entity.updatedBy,
            updatedAt = entity.updatedAt
        )
    }

    fun toEntity(model: QuizAnswer, quizEntity: QuizEntity): QuizAnswerEntity {
        return QuizAnswerEntity(
            id = model.id,
            answer = model.answer,
            sortOrder = model.sortOrder,
            quiz = quizEntity
        ).apply {
            createdBy = model.createdBy
            createdAt = model.createdAt
            updatedBy = model.updatedBy
            updatedAt = model.updatedAt
        }
    }

    fun toModel(command: CreateQuizAnswerCommand): QuizAnswer {
        return QuizAnswer(
            answer = command.answer,
            sortOrder = command.sortOrder,
            quizId = command.quizId
        )
    }

    fun toModel(command: UpdateQuizAnswerCommand, existing: QuizAnswer): QuizAnswer {
        return existing.copy(
            answer = command.answer ?: existing.answer,
            sortOrder = command.sortOrder ?: existing.sortOrder
        )
    }
} 