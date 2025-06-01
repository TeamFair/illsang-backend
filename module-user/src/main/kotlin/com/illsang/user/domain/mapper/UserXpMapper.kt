package com.illsang.user.domain.mapper

import com.teamfair.moduleuser.adapter.out.persistence.entity.UserEntity
import com.teamfair.moduleuser.adapter.out.persistence.entity.UserXpEntity
import com.teamfair.moduleuser.domain.model.UserXpModel

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

    fun toEntity(model: UserXpModel, user: UserEntity): UserXpEntity {
        return UserXpEntity(
            id = model.id,
            userEntity = user,
            xpType = model.xpType,
            point = model.point
        ).apply {
            createdBy = model.createdBy
            createdAt = model.createdAt
            updatedBy = model.updatedBy
            updatedAt = model.updatedAt
        }
    }
} 