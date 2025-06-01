package com.teamfair.modulequest.domain.mapper

import com.teamfair.modulequest.adapter.out.persistence.entity.QuizAnswerEntity
import com.teamfair.modulequest.adapter.out.persistence.entity.QuizEntity
import com.teamfair.modulequest.domain.model.QuizAnswer

object QuizAnswerMapper {
    fun toModel(entity: QuizAnswerEntity): QuizAnswer {
        return QuizAnswer(
            id = entity.id,
            answer = entity.answer,
            sortOrder = entity.sortOrder,
            createdBy = entity.createdBy,
            createdAt = entity.createdAt?.toString(),
            updatedBy = entity.updatedBy,
            updatedAt = entity.updatedAt?.toString()
        )
    }

    fun toEntity(model: QuizAnswer, quiz: QuizEntity): QuizAnswerEntity {
        return QuizAnswerEntity(
            id = model.id,
            quiz = quiz,
            answer = model.answer,
            sortOrder = model.sortOrder
        )
    }
} 