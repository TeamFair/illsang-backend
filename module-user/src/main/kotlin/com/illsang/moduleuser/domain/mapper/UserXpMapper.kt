package com.illsang.moduleuser.domain.mapper

import com.illsang.moduleuser.adapter.out.persistence.entity.UserEntity
import com.illsang.moduleuser.adapter.out.persistence.entity.UserXpEntity
import com.illsang.moduleuser.application.command.CreateUserXpCommand
import com.illsang.moduleuser.application.command.UpdateUserXpCommand
import com.illsang.moduleuser.domain.model.UserXpModel

object UserXpMapper {
    fun toModel(entity: UserXpEntity): UserXpModel {
        return UserXpModel(
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

    fun toEntity(model: UserXpModel, userEntity: UserEntity): UserXpEntity {
        return UserXpEntity(
            id = model.id,
            xpType = model.xpType,
            userEntity = userEntity,
            point = model.point
        ).apply {
            createdBy = model.createdBy
            createdAt = model.createdAt
            updatedBy = model.updatedBy
            updatedAt = model.updatedAt
        }
    }

    fun toModel(command: CreateUserXpCommand): UserXpModel {
        return UserXpModel(
            userId = command.userId,
            xpType = command.xpType,
            point = command.point
        )
    }

    fun toModel(command: UpdateUserXpCommand, existing: UserXpModel): UserXpModel {
        return existing.copy(
            xpType = command.xpType ?: existing.xpType,
            point = command.point ?: existing.point
        )
    }
}
