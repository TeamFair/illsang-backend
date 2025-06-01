package com.teamfair.modulequest.domain.mapper

import com.teamfair.modulequest.adapter.out.persistence.entity.QuizEntity
import com.teamfair.modulequest.adapter.out.persistence.entity.UserMissionHistoryEntity
import com.teamfair.modulequest.adapter.out.persistence.entity.UserQuizHistoryEntity
import com.teamfair.modulequest.domain.model.UserQuizHistory

object UserQuizHistoryMapper {
    fun toModel(entity: UserQuizHistoryEntity): UserQuizHistory {
        return UserQuizHistory(
            id = entity.id,
            userId = entity.userId,
            answer = entity.answer,
            submittedAt = entity.submittedAt,
            createdBy = entity.createdBy,
            createdAt = entity.createdAt?.toString(),
            updatedBy = entity.updatedBy,
            updatedAt = entity.updatedAt?.toString()
        )
    }

    fun toEntity(
        model: UserQuizHistory,
        quiz: QuizEntity,
        userMissionHistory: UserMissionHistoryEntity
    ): UserQuizHistoryEntity {
        return UserQuizHistoryEntity(
            id = model.id,
            userId = model.userId,
            quiz = quiz,
            userMissionHistory = userMissionHistory,
            answer = model.answer,
            submittedAt = model.submittedAt
        )
    }
} 