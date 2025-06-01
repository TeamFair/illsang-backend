package com.illsang.user.domain.mapper

import com.teamfair.moduleuser.adapter.out.persistence.entity.UserEntity
import com.teamfair.moduleuser.adapter.out.persistence.entity.UserXpHistoryEntity
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

    fun toEntity(model: UserXpHistoryModel, user: UserEntity): UserXpHistoryEntity {
        return UserXpHistoryEntity(
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