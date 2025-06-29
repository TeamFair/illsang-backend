package com.teamfair.moduleuser.domain.mapper

import com.teamfair.moduleuser.adapter.out.persistence.entity.UserEmojiEntity
import com.teamfair.moduleuser.application.command.CreateUserEmojiCommand
import com.teamfair.moduleuser.application.command.UpdateUserEmojiCommand
import com.teamfair.moduleuser.domain.model.UserEmojiModel

object UserEmojiMapper {
    fun toModel(entity: UserEmojiEntity): UserEmojiModel {
        return UserEmojiModel(
            id = entity.id,
            userId = entity.userId,
            emojiId = entity.emojiId,
            isEquipped = entity.isEquipped,
            createdBy = entity.createdBy,
            createdAt = entity.createdAt,
            updatedBy = entity.updatedBy,
            updatedAt = entity.updatedAt,
            targetId = entity.targetId
        )
    }

    fun toEntity(model: UserEmojiModel): UserEmojiEntity {
        return UserEmojiEntity(
            id = model.id,
            userId = model.userId,
            emojiId = model.emojiId,
            isEquipped = model.isEquipped,
            targetId = model.targetId
        ).apply {
            createdBy = model.createdBy
            createdAt = model.createdAt
            updatedBy = model.updatedBy
            updatedAt = model.updatedAt
        }
    }


    fun toModel(command: CreateUserEmojiCommand): UserEmojiModel {
        return UserEmojiModel(
            userId = command.userId,
            emojiId = command.emojiId,
            isEquipped = command.isEquipped,
            targetId = command.targetId
        )
    }

    fun toModel(command: UpdateUserEmojiCommand, existing: UserEmojiModel): UserEmojiModel {
        return existing.copy(
            userId = command.userId,
            emojiId = command.emojiId,
            isEquipped = command.isEquipped,
            targetId = command.targetId
        )
    }
} 