package com.teamfair.modulequest.domain.mapper

import com.teamfair.modulequest.adapter.out.persistence.entity.QuestEntity
import com.teamfair.modulequest.domain.model.Quest

object QuestMapper {
    fun toModel(entity: QuestEntity): Quest {
        return Quest(
            id = entity.id,
            title = entity.title,
            description = entity.description,
            imageUrl = entity.imageUrl,
            isRepeatable = entity.isRepeatable,
            isPublished = entity.isPublished,
            missions = entity.missions.map { MissionMapper.toModel(it) }.toMutableList(),
            rewards = entity.rewards.map { QuestRewardMapper.toModel(it) }.toMutableList(),
            userHistories = entity.userHistories.map { UserQuestHistoryMapper.toModel(it) }.toMutableList(),
            createdBy = entity.createdBy,
            createdAt = entity.createdAt?.toString(),
            updatedBy = entity.updatedBy,
            updatedAt = entity.updatedAt?.toString()
        )
    }

    fun toEntity(model: Quest): QuestEntity {
        return QuestEntity(
            id = model.id,
            title = model.title,
            description = model.description,
            imageUrl = model.imageUrl,
            isRepeatable = model.isRepeatable,
            isPublished = model.isPublished
        ).apply {
            model.missions.forEach { addMission(MissionMapper.toEntity(it, this)) }
            model.rewards.forEach { addReward(QuestRewardMapper.toEntity(it, this)) }
        }
    }
} 