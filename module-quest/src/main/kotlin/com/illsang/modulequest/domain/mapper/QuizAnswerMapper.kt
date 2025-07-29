package com.illsang.modulequest.domain.mapper

import com.illsang.modulequest.adapter.out.persistence.entity.QuizAnswerEntity
import com.illsang.modulequest.adapter.out.persistence.entity.QuizEntity
import com.illsang.modulequest.application.command.CreateQuizAnswerCommand
import com.illsang.modulequest.application.command.UpdateQuizAnswerCommand
import com.illsang.modulequest.domain.model.QuizAnswerModel

object QuizAnswerMapper {
    fun toModel(entity: QuizAnswerEntity): QuizAnswerModel {
        return QuizAnswerModel(
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

    fun toEntity(model: QuizAnswerModel, quizEntity: QuizEntity): QuizAnswerEntity {
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

    fun toModel(command: CreateQuizAnswerCommand): QuizAnswerModel {
        return QuizAnswerModel(
            answer = command.answer,
            sortOrder = command.sortOrder,
            quizId = command.quizId
        )
    }

    fun toModel(command: UpdateQuizAnswerCommand, existing: QuizAnswerModel): QuizAnswerModel {
        return existing.copy(
            answer = command.answer ?: existing.answer,
            sortOrder = command.sortOrder ?: existing.sortOrder
        )
    }
}
