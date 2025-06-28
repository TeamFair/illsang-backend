package com.teamfair.modulequest.domain.mapper

import com.teamfair.modulequest.adapter.out.persistence.entity.MissionEntity
import com.teamfair.modulequest.adapter.out.persistence.entity.UserMissionHistoryEntity
import com.teamfair.modulequest.adapter.out.persistence.entity.UserQuestHistoryEntity
import com.teamfair.modulequest.application.command.CreateUserMissionHistoryCommand
import com.teamfair.modulequest.application.command.UpdateUserMissionHistoryCommand
import com.teamfair.modulequest.domain.model.UserMissionHistory

object UserMissionHistoryMapper {
    fun toModel(entity: UserMissionHistoryEntity): UserMissionHistory {
        return UserMissionHistory(
            id = entity.id,
            userId = entity.userId,
            status = entity.status,
            submissionImageUrl = entity.submissionImageUrl,
            submittedAt = entity.submittedAt,
            missionId = entity.mission.id!!,
            userQuestHistoryId = entity.userQuestHistory.id!!,
            createdBy = entity.createdBy,
            createdAt = entity.createdAt,
            updatedBy = entity.updatedBy,
            updatedAt = entity.updatedAt
        )
    }

    fun toEntity(model: UserMissionHistory, missionEntity: MissionEntity, userQuestHistoryEntity: UserQuestHistoryEntity): UserMissionHistoryEntity {
        return UserMissionHistoryEntity(
            id = model.id,
            userId = model.userId,
            status = model.status,
            submissionImageUrl = model.submissionImageUrl,
            submittedAt = model.submittedAt,
            mission = missionEntity,
            userQuestHistory = userQuestHistoryEntity
        ).apply {
            createdBy = model.createdBy
            createdAt = model.createdAt
            updatedBy = model.updatedBy
            updatedAt = model.updatedAt
        }
    }

    fun toModel(command: CreateUserMissionHistoryCommand): UserMissionHistory {
        return UserMissionHistory(
            userId = command.userId,
            status = command.status,
            submissionImageUrl = command.submissionImageUrl,
            submittedAt = command.submittedAt,
            missionId = command.missionId,
            userQuestHistoryId = command.userQuestHistoryId
        )
    }

    fun toModel(command: UpdateUserMissionHistoryCommand, existing: UserMissionHistory): UserMissionHistory {
        return existing.copy(
            status = command.status ?: existing.status,
            submissionImageUrl = command.submissionImageUrl ?: existing.submissionImageUrl,
            submittedAt = command.submittedAt ?: existing.submittedAt
        )
    }
} 