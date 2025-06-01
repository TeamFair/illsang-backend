package com.teamfair.modulequest.domain.mapper

import com.teamfair.modulequest.adapter.out.persistence.entity.MissionEntity
import com.teamfair.modulequest.adapter.out.persistence.entity.UserMissionHistoryEntity
import com.teamfair.modulequest.adapter.out.persistence.entity.UserQuestHistoryEntity
import com.teamfair.modulequest.domain.model.UserMissionHistory

object UserMissionHistoryMapper {
    fun toModel(entity: UserMissionHistoryEntity): UserMissionHistory {
        return UserMissionHistory(
            id = entity.id,
            userId = entity.userId,
            status = entity.status,
            submissionImageUrl = entity.submissionImageUrl,
            submittedAt = entity.submittedAt,
            quizHistories = entity.quizHistories.map { UserQuizHistoryMapper.toModel(it) }.toMutableList(),
            createdBy = entity.createdBy,
            createdAt = entity.createdAt?.toString(),
            updatedBy = entity.updatedBy,
            updatedAt = entity.updatedAt?.toString()
        )
    }

    fun toEntity(
        model: UserMissionHistory,
        mission: MissionEntity,
        userQuestHistory: UserQuestHistoryEntity
    ): UserMissionHistoryEntity {
        return UserMissionHistoryEntity(
            id = model.id,
            userId = model.userId,
            mission = mission,
            userQuestHistory = userQuestHistory,
            status = model.status,
            submissionImageUrl = model.submissionImageUrl,
            submittedAt = model.submittedAt
        )
    }
} 