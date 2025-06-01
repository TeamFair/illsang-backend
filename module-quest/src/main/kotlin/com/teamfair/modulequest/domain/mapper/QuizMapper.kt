package com.teamfair.modulequest.domain.mapper

import com.teamfair.modulequest.adapter.out.persistence.entity.MissionEntity
import com.teamfair.modulequest.adapter.out.persistence.entity.QuizEntity
import com.teamfair.modulequest.domain.model.Quiz

object QuizMapper {
    fun toModel(entity: QuizEntity): Quiz {
        return Quiz(
            id = entity.id,
            question = entity.question,
            hint = entity.hint,
            sortOrder = entity.sortOrder,
            answers = entity.answers.map { QuizAnswerMapper.toModel(it) }.toMutableList(),
            userHistories = entity.userHistories.map { UserQuizHistoryMapper.toModel(it) }.toMutableList(),
            createdBy = entity.createdBy,
            createdAt = entity.createdAt?.toString(),
            updatedBy = entity.updatedBy,
            updatedAt = entity.updatedAt?.toString()
        )
    }

    fun toEntity(model: Quiz, mission: MissionEntity): QuizEntity {
        return QuizEntity(
            id = model.id,
            mission = mission,
            question = model.question,
            hint = model.hint,
            sortOrder = model.sortOrder
        ).apply {
            model.answers.forEach { addAnswer(QuizAnswerMapper.toEntity(it, this)) }
        }
    }
} 