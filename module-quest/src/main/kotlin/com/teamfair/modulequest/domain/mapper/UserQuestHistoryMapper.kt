package com.teamfair.modulequest.domain.mapper

import com.teamfair.modulequest.adapter.out.persistence.entity.QuestEntity
import com.teamfair.modulequest.adapter.out.persistence.entity.UserQuestHistoryEntity
import com.teamfair.modulequest.application.command.CreateUserQuestHistoryCommand
import com.teamfair.modulequest.application.command.UpdateUserQuestHistoryCommand
import com.teamfair.modulequest.domain.model.UserQuestHistoryModel

object UserQuestHistoryMapper {
    fun toModel(entity: UserQuestHistoryEntity): UserQuestHistoryModel {
        return UserQuestHistoryModel(
            id = entity.id,
            userId = entity.userId,
            status = entity.status,
            liked = entity.liked,
            disliked = entity.disliked,
            viewCount = entity.viewCount,
            questId = entity.quest.id!!,
            createdBy = entity.createdBy,
            createdAt = entity.createdAt,
            updatedBy = entity.updatedBy,
            updatedAt = entity.updatedAt
        )
    }

    fun toEntity(model: UserQuestHistoryModel, questEntity: QuestEntity): UserQuestHistoryEntity {
        return UserQuestHistoryEntity(
            id = model.id,
            userId = model.userId,
            status = model.status,
            liked = model.liked,
            disliked = model.disliked,
            viewCount = model.viewCount,
            quest = questEntity
        ).apply {
            createdBy = model.createdBy
            createdAt = model.createdAt
            updatedBy = model.updatedBy
            updatedAt = model.updatedAt
        }
    }

    fun toModel(command: CreateUserQuestHistoryCommand): UserQuestHistoryModel {
        return UserQuestHistoryModel(
            userId = command.userId,
            status = command.status,
            liked = command.liked,
            disliked = command.disliked,
            viewCount = command.viewCount,
            questId = command.questId
        )
    }

    fun toModel(command: UpdateUserQuestHistoryCommand, existing: UserQuestHistoryModel): UserQuestHistoryModel {
        return existing.copy(
            status = command.status ?: existing.status,
            liked = command.liked ?: existing.liked,
            disliked = command.disliked ?: existing.disliked,
            viewCount = command.viewCount ?: existing.viewCount
        )
    }
} 