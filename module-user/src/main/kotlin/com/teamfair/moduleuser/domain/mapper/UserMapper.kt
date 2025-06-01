package com.teamfair.moduleuser.domain.mapper

import com.teamfair.moduleuser.adapter.out.persistence.entity.UserEntity
import com.teamfair.moduleuser.domain.model.UserModel

object UserMapper {
    fun toModel(entity: UserEntity): UserModel {
        return UserModel(
            id = entity.id,
            email = entity.email,
            nickname = entity.nickname,
            status = entity.status,
            statusUpdatedAt = entity.statusUpdatedAt,
            profileImageId = entity.profileImageId,
            userXps = entity.userXpEntities.map { UserXpMapper.toModel(it) },
            xpHistories = entity.xpHistories.map { UserXpHistoryMapper.toModel(it) },
            emojis = entity.emojis.map { UserEmojiMapper.toModel(it) },
            createdBy = entity.createdBy,
            createdAt = entity.createdAt,
            updatedBy = entity.updatedBy,
            updatedAt = entity.updatedAt
        )
    }

    fun toEntity(model: UserModel): UserEntity {
        return UserEntity(
            id = model.id,
            email = model.email,
            nickname = model.nickname,
            status = model.status,
            statusUpdatedAt = model.statusUpdatedAt,
            profileImageId = model.profileImageId
        ).apply {
            createdBy = model.createdBy
            createdAt = model.createdAt
            updatedBy = model.updatedBy
            updatedAt = model.updatedAt
        }
    }
} 