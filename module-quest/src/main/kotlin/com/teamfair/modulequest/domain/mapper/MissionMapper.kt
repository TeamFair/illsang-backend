package com.teamfair.modulequest.domain.mapper

import com.teamfair.modulequest.adapter.out.persistence.entity.MissionEntity
import com.teamfair.modulequest.adapter.out.persistence.entity.QuestEntity
import com.teamfair.modulequest.application.command.CreateMissionCommand
import com.teamfair.modulequest.application.command.UpdateMissionCommand
import com.teamfair.modulequest.domain.model.Mission

object MissionMapper {
    fun toModel(entity: MissionEntity): Mission {
        return Mission(
            id = entity.id,
            type = entity.type,
            title = entity.title,
            sortOrder = entity.sortOrder,
            questId = entity.quest.id!!,
            createdBy = entity.createdBy,
            createdAt = entity.createdAt,
            updatedBy = entity.updatedBy,
            updatedAt = entity.updatedAt
        )
    }

    fun toEntity(model: Mission, questEntity: QuestEntity): MissionEntity {
        return MissionEntity(
            id = model.id,
            type = model.type,
            title = model.title,
            sortOrder = model.sortOrder,
            quest = questEntity
        ).apply {
            createdBy = model.createdBy
            createdAt = model.createdAt
            updatedBy = model.updatedBy
            updatedAt = model.updatedAt
        }
    }

    fun toModel(command: CreateMissionCommand): Mission {
        return Mission(
            type = command.type,
            title = command.title,
            sortOrder = command.sortOrder,
            questId = command.questId
        )
    }

    fun toModel(command: UpdateMissionCommand, existing: Mission): Mission {
        return existing.copy(
            type = command.type ?: existing.type,
            title = command.title ?: existing.title,
            sortOrder = command.sortOrder ?: existing.sortOrder
        )
    }
} 