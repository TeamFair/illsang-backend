package com.teamfair.moduleuser.domain.mapper

import com.teamfair.moduleuser.adapter.out.persistence.entity.UserEntity
import com.teamfair.moduleuser.adapter.out.persistence.entity.UserEmojiEntity
import com.teamfair.moduleuser.domain.model.UserEmojiModel

object UserEmojiMapper {
    fun toModel(entity: UserEmojiEntity): UserEmojiModel {
        return UserEmojiModel(
            id = entity.id,
            userId = entity.userEntity.id!!,
            targetId = entity.targetId,
            targetType = entity.targetType,
            createdBy = entity.createdBy,
            createdAt = entity.createdAt,
            updatedBy = entity.updatedBy,
            updatedAt = entity.updatedAt
        )
    }

    fun toEntity(model: UserEmojiModel, userEntity: UserEntity): UserEmojiEntity {
        return UserEmojiEntity(
            id = model.id,
            userEntity = userEntity,
            targetId = model.targetId,
            targetType = model.targetType
        ).apply {
            createdBy = model.createdBy
            createdAt = model.createdAt
            updatedBy = model.updatedBy
            updatedAt = model.updatedAt
        }
    }
} 