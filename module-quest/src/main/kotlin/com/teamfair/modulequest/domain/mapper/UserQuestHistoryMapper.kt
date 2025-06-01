package com.teamfair.modulequest.domain.mapper

import com.teamfair.modulequest.adapter.out.persistence.entity.QuestEntity
import com.teamfair.modulequest.adapter.out.persistence.entity.UserQuestHistoryEntity
import com.teamfair.modulequest.domain.model.UserQuestHistory

object UserQuestHistoryMapper {
    fun toModel(entity: UserQuestHistoryEntity): UserQuestHistory {
        return UserQuestHistory(
            id = entity.id,
            userId = entity.userId,
            status = entity.status,
            liked = entity.liked,
            disliked = entity.disliked,
            viewCount = entity.viewCount,
            missionHistories = entity.missionHistories.map { UserMissionHistoryMapper.toModel(it) }.toMutableList(),
            createdBy = entity.createdBy,
            createdAt = entity.createdAt?.toString(),
            updatedBy = entity.updatedBy,
            updatedAt = entity.updatedAt?.toString()
        )
    }

    fun toEntity(model: UserQuestHistory, quest: QuestEntity): UserQuestHistoryEntity {
        return UserQuestHistoryEntity(
            id = model.id,
            userId = model.userId,
            quest = quest,
            status = model.status,
            liked = model.liked,
            disliked = model.disliked,
            viewCount = model.viewCount
        )
    }
} 