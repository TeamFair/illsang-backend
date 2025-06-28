package com.illsang.management.domain.mapper

import com.illsang.management.adapter.out.persistence.entity.ImageEntity
import com.illsang.management.domain.model.ImageModel

object ImageMapper {
    fun toModel(entity: ImageEntity): ImageModel {
        return ImageModel(
            id = entity.id,
            type = entity.type,
            name = entity.name,
            size = entity.size,
            createdBy = entity.createdBy,
            createdAt = entity.createdAt,
            updatedBy = entity.updatedBy,
            updatedAt = entity.updatedAt
        )
    }

    fun toEntity(model: ImageModel): ImageEntity {
        return ImageEntity(
            id = model.id,
            type = model.type,
            name = model.name,
            size = model.size
        ).apply {
            createdBy = model.createdBy
            createdAt = model.createdAt
            updatedBy = model.updatedBy
            updatedAt = model.updatedAt
        }
    }
} 