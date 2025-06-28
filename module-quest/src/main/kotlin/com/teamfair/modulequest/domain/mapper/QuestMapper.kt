package com.teamfair.modulequest.domain.mapper

import com.teamfair.modulequest.adapter.out.persistence.entity.QuestEntity
import com.teamfair.modulequest.domain.model.Quest

object QuestMapper {
    fun toModel(entity: QuestEntity): Quest {
        return Quest(
            id = entity.id,
            imageId = entity.imageId,
            writerName = entity.writerName,
            mainImageId = entity.mainImageId,
            popularYn = entity.popularYn,
            type = entity.type,
            repeatFrequency = entity.repeatFrequency,
            sortOrder = entity.sortOrder,
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
            imageId = model.imageId,
            writerName = model.writerName,
            mainImageId = model.mainImageId,
            popularYn = model.popularYn,
            type = model.type,
            repeatFrequency = model.repeatFrequency,
            sortOrder = model.sortOrder
        ).apply {
            model.missions.forEach { addMission(MissionMapper.toEntity(it, this)) }
            model.rewards.forEach { addReward(QuestRewardMapper.toEntity(it, this)) }
        }
    }
} 