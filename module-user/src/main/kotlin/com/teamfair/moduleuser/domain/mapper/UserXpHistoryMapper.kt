package com.teamfair.moduleuser.domain.mapper

import com.teamfair.moduleuser.adapter.out.persistence.entity.UserEntity
import com.teamfair.moduleuser.adapter.out.persistence.entity.UserXpHistoryEntity
import com.teamfair.moduleuser.application.command.CreateUserXpHistoryCommand
import com.teamfair.moduleuser.application.command.UpdateUserXpHistoryCommand
import com.teamfair.moduleuser.domain.model.UserXpHistoryModel

object UserXpHistoryMapper {
    fun toModel(entity: UserXpHistoryEntity): UserXpHistoryModel {
        return UserXpHistoryModel(
            id = entity.id,
            userId = entity.userEntity.id!!,
            xpType = entity.xpType,
            point = entity.point,
            createdBy = entity.createdBy,
            createdAt = entity.createdAt,
            updatedBy = entity.updatedBy,
            updatedAt = entity.updatedAt
        )
    }

    fun toEntity(model: UserXpHistoryModel, userEntity: UserEntity): UserXpHistoryEntity {
        return UserXpHistoryEntity(
            id = model.id,
            userEntity = userEntity,
            xpType = model.xpType,
            point = model.point
        ).apply {
            createdBy = model.createdBy
            createdAt = model.createdAt
            updatedBy = model.updatedBy
            updatedAt = model.updatedAt
        }
    }

    fun toModel(command: CreateUserXpHistoryCommand): UserXpHistoryModel {
        return UserXpHistoryModel(
            userId = command.userId,
            xpType = command.xpType,
            point = command.point
        )
    }

    fun toModel(command: UpdateUserXpHistoryCommand, existing: UserXpHistoryModel): UserXpHistoryModel {
        return existing.copy(
            xpType = command.xpType ?: existing.xpType,
            point = command.point ?: existing.point
        )
    }
} 