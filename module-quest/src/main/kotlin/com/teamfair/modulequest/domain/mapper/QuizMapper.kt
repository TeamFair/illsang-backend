package com.teamfair.modulequest.domain.mapper

import com.teamfair.modulequest.adapter.out.persistence.entity.MissionEntity
import com.teamfair.modulequest.adapter.out.persistence.entity.QuizEntity
import com.teamfair.modulequest.application.command.CreateQuizCommand
import com.teamfair.modulequest.application.command.UpdateQuizCommand
import com.teamfair.modulequest.domain.model.QuizModel

object QuizMapper {
    fun toModel(entity: QuizEntity): QuizModel {
        return QuizModel(
            id = entity.id,
            question = entity.question,
            hint = entity.hint,
            sortOrder = entity.sortOrder,
            missionId = entity.mission.id!!,
            createdBy = entity.createdBy,
            createdAt = entity.createdAt,
            updatedBy = entity.updatedBy,
            updatedAt = entity.updatedAt
        )
    }

    fun toEntity(model: QuizModel, missionEntity: MissionEntity): QuizEntity {
        return QuizEntity(
            id = model.id,
            question = model.question,
            hint = model.hint,
            sortOrder = model.sortOrder,
            mission = missionEntity
        ).apply {
            createdBy = model.createdBy
            createdAt = model.createdAt
            updatedBy = model.updatedBy
            updatedAt = model.updatedAt
        }
    }

    fun toModel(command: CreateQuizCommand): QuizModel {
        return QuizModel(
            question = command.question,
            hint = command.hint,
            sortOrder = command.sortOrder,
            missionId = command.missionId
        )
    }

    fun toModel(command: UpdateQuizCommand, existing: QuizModel): QuizModel {
        return existing.copy(
            question = command.question ?: existing.question,
            hint = command.hint ?: existing.hint,
            sortOrder = command.sortOrder ?: existing.sortOrder
        )
    }
} 