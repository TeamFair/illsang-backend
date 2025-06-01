package com.teamfair.modulequest.domain.mapper

import com.teamfair.modulequest.adapter.out.persistence.entity.QuestEntity
import com.teamfair.modulequest.adapter.out.persistence.entity.QuestRewardEntity
import com.teamfair.modulequest.domain.model.QuestReward

object QuestRewardMapper {
    fun toModel(entity: QuestRewardEntity): QuestReward {
        return QuestReward(
            id = entity.id,
            type = entity.type,
            amount = entity.amount,
            createdBy = entity.createdBy,
            createdAt = entity.createdAt?.toString(),
            updatedBy = entity.updatedBy,
            updatedAt = entity.updatedAt?.toString()
        )
    }

    fun toEntity(model: QuestReward, quest: QuestEntity): QuestRewardEntity {
        return QuestRewardEntity(
            id = model.id,
            quest = quest,
            type = model.type,
            amount = model.amount
        )
    }
} 