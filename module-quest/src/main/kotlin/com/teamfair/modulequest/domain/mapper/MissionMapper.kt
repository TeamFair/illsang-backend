package com.teamfair.modulequest.domain.mapper

import com.teamfair.modulequest.adapter.out.persistence.entity.MissionEntity
import com.teamfair.modulequest.adapter.out.persistence.entity.QuestEntity
import com.teamfair.modulequest.domain.model.Mission

object MissionMapper {
    fun toModel(entity: MissionEntity): Mission {
        return Mission(
            id = entity.id,
            type = entity.type,
            title = entity.title,
            sortOrder = entity.sortOrder,
            quizzes = entity.quizzes.map { QuizMapper.toModel(it) }.toMutableList(),
            userHistories = entity.userHistories.map { UserMissionHistoryMapper.toModel(it) }.toMutableList(),
            createdBy = entity.createdBy,
            createdAt = entity.createdAt?.toString(),
            updatedBy = entity.updatedBy,
            updatedAt = entity.updatedAt?.toString()
        )
    }

    fun toEntity(model: Mission, quest: QuestEntity): MissionEntity {
        return MissionEntity(
            id = model.id,
            type = model.type,
            quest = quest,
            title = model.title,
            sortOrder = model.sortOrder
        ).apply {
            model.quizzes.forEach { addQuiz(QuizMapper.toEntity(it, this)) }
        }
    }
} 