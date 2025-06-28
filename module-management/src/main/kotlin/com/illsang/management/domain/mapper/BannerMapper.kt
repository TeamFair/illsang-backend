package com.illsang.management.domain.mapper

import com.illsang.management.adapter.out.persistence.entity.BannerEntity
import com.illsang.management.domain.model.BannerModel

object BannerMapper {
    fun toModel(entity: BannerEntity): BannerModel {
        return BannerModel(
            id = entity.id,
            title = entity.title,
            bannerImageId = entity.bannerImageId,
            description = entity.description,
            activeYn = entity.activeYn,
            createdBy = entity.createdBy,
            createdAt = entity.createdAt,
            updatedBy = entity.updatedBy,
            updatedAt = entity.updatedAt
        )
    }

    fun toEntity(model: BannerModel): BannerEntity {
        return BannerEntity(
            id = model.id,
            title = model.title,
            bannerImageId = model.bannerImageId,
            description = model.description,
            activeYn = model.activeYn
        ).apply {
            createdBy = model.createdBy
            createdAt = model.createdAt
            updatedBy = model.updatedBy
            updatedAt = model.updatedAt
        }
    }
} 