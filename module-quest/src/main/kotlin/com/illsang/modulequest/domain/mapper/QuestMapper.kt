package com.illsang.modulequest.domain.mapper

import com.illsang.modulequest.adapter.out.persistence.entity.QuestEntity
import com.illsang.modulequest.application.command.CreateQuestCommand
import com.illsang.modulequest.application.command.UpdateQuestCommand
import com.illsang.modulequest.domain.model.QuestModel

object QuestMapper {
    fun toModel(entity: QuestEntity): QuestModel {
        return QuestModel(
            id = entity.id,
            imageId = entity.imageId,
            writerName = entity.writerName,
            mainImageId = entity.mainImageId,
            popularYn = entity.popularYn,
            type = entity.type,
            repeatFrequency = entity.repeatFrequency,
            sortOrder = entity.sortOrder,
            createdBy = entity.createdBy,
            createdAt = entity.createdAt,
            updatedBy = entity.updatedBy,
            updatedAt = entity.updatedAt
        )
    }

    fun toEntity(model: QuestModel): QuestEntity {
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
            createdBy = model.createdBy
            createdAt = model.createdAt
            updatedBy = model.updatedBy
            updatedAt = model.updatedAt
        }
    }

    fun toModel(command: CreateQuestCommand): QuestModel {
        return QuestModel(
            imageId = command.imageId,
            writerName = command.writerName,
            mainImageId = command.mainImageId,
            popularYn = command.popularYn,
            type = command.type,
            repeatFrequency = command.repeatFrequency,
            sortOrder = command.sortOrder
        )
    }

    fun toModel(command: UpdateQuestCommand, existing: QuestModel): QuestModel {
        return existing.copy(
            imageId = command.imageId ?: existing.imageId,
            writerName = command.writerName ?: existing.writerName,
            mainImageId = command.mainImageId ?: existing.mainImageId,
            popularYn = command.popularYn ?: existing.popularYn,
            type = command.type ?: existing.type,
            repeatFrequency = command.repeatFrequency ?: existing.repeatFrequency,
            sortOrder = command.sortOrder ?: existing.sortOrder
        )
    }
}
