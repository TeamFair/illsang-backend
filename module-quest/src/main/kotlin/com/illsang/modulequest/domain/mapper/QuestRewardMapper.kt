package com.illsang.modulequest.domain.mapper

import com.illsang.modulequest.adapter.out.persistence.entity.QuestEntity
import com.illsang.modulequest.adapter.out.persistence.entity.QuestRewardEntity
import com.illsang.modulequest.application.command.CreateQuestRewardCommand
import com.illsang.modulequest.application.command.UpdateQuestRewardCommand
import com.illsang.modulequest.domain.model.QuestRewardModel

object QuestRewardMapper {
    fun toModel(entity: QuestRewardEntity): QuestRewardModel {
        return QuestRewardModel(
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

    fun toEntity(model: QuestRewardModel, questEntity: QuestEntity): QuestRewardEntity {
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

    fun toModel(command: CreateQuestRewardCommand): QuestRewardModel {
        return QuestRewardModel(
            type = command.type,
            amount = command.amount,
            questId = command.questId
        )
    }

    fun toModel(command: UpdateQuestRewardCommand, existing: QuestRewardModel): QuestRewardModel {
        return existing.copy(
            type = command.type ?: existing.type,
            amount = command.amount ?: existing.amount
        )
    }
}
