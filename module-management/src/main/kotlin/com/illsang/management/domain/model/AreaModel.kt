package com.illsang.management.domain.model

import com.illsang.management.domain.entity.CommercialAreaEntity
import com.illsang.management.domain.entity.MetroAreaEntity
import java.time.LocalDateTime

data class MetroAreaModel(
    val code: String,
    val areaName: String,
    val images: List<String>,
    val commercialAreaModel: List<CommercialAreaModel>,
    val createdBy: String?,
    val createdAt: LocalDateTime?,
    val updatedBy: String?,
    val updatedAt: LocalDateTime?,
) {

    companion object {
        fun from(metroArea: MetroAreaEntity): MetroAreaModel {
            return MetroAreaModel(
                code = metroArea.code,
                areaName = metroArea.areaName,
                commercialAreaModel = metroArea.commercialAreas.map { CommercialAreaModel.from(it) },
                images = metroArea.images,
                createdBy = metroArea.createdBy,
                createdAt = metroArea.createdAt,
                updatedBy = metroArea.updatedBy,
                updatedAt = metroArea.updatedAt,
            )
        }
    }

}

data class CommercialAreaModel(
    val code: String,
    val areaName: String,
    val description: String?,
    val metroAreaCode: String,
    val images: List<String>,
    val createdBy: String?,
    val createdAt: LocalDateTime?,
    val updatedBy: String?,
    val updatedAt: LocalDateTime?,
) {

    companion object {
        fun from(commercialArea: CommercialAreaEntity): CommercialAreaModel {
            return CommercialAreaModel(
                code = commercialArea.code,
                areaName = commercialArea.areaName,
                description = commercialArea.description,
                metroAreaCode = commercialArea.metroArea.code,
                images = commercialArea.images,
                createdBy = commercialArea.createdBy,
                createdAt = commercialArea.createdAt,
                updatedBy = commercialArea.updatedBy,
                updatedAt = commercialArea.updatedAt,
            )
        }
    }

}
