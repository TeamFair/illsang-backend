package com.teamfair.modulequest.domain.mapper

import com.teamfair.modulequest.adapter.out.persistence.entity.QuestEntity
import com.teamfair.modulequest.adapter.out.persistence.entity.QuestRewardEntity
import com.teamfair.modulequest.application.command.CreateQuestRewardCommand
import com.teamfair.modulequest.application.command.UpdateQuestRewardCommand
import com.teamfair.modulequest.domain.model.QuestReward

object QuestRewardMapper {
    fun toModel(entity: QuestRewardEntity): QuestReward {
        return QuestReward(
            id = entity.id,
            type = entity.type,
            amount = entity.amount,
            questId = entity.quest.id!!,
            createdBy = entity.createdBy,
            createdAt = entity.createdAt,
            updatedBy = entity.updatedBy,
            updatedAt = entity.updatedAt
        )
    }

    fun toEntity(model: QuestReward, questEntity: QuestEntity): QuestRewardEntity {
        return QuestRewardEntity(
            id = model.id,
            type = model.type,
            amount = model.amount,
            quest = questEntity
        ).apply {
            createdBy = model.createdBy
            createdAt = model.createdAt
            updatedBy = model.updatedBy
            updatedAt = model.updatedAt
        }
    }

    fun toModel(command: CreateQuestRewardCommand): QuestReward {
        return QuestReward(
            type = command.type,
            amount = command.amount,
            questId = command.questId
        )
    }

    fun toModel(command: UpdateQuestRewardCommand, existing: QuestReward): QuestReward {
        return existing.copy(
            type = command.type ?: existing.type,
            amount = command.amount ?: existing.amount
        )
    }
} 