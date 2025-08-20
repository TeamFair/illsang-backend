package com.illsang.management.dto.response

import com.illsang.management.domain.model.CommercialAreaModel
import com.illsang.management.domain.model.MetroAreaModel
import java.time.LocalDateTime

data class MetroAreaResponse(
    val code: String?,
    val areaName: String?,
    val images: List<String>,
    val commercialAreas: List<CommercialAreaResponse>,
    val createdBy: String?,
    val createdAt: LocalDateTime?,
    val updatedBy: String?,
    val updatedAt: LocalDateTime?,
) {
    companion object {
        fun from(metroArea: MetroAreaModel): MetroAreaResponse {
            return MetroAreaResponse(
                code = metroArea.code,
                areaName = metroArea.areaName,
                images = metroArea.images,
                commercialAreas = metroArea.commercialAreaModel.map { CommercialAreaResponse.from(it) },
                createdBy = metroArea.createdBy,
                createdAt = metroArea.createdAt,
                updatedBy = metroArea.updatedBy,
                updatedAt = metroArea.updatedAt,
            )
        }
    }
}

data class CommercialAreaResponse(
    val code: String?,
    val areaName: String?,
    val description: String?,
    val images: List<String>,
    val metroAreaCode: String?,
    val createdBy: String?,
    val createdAt: LocalDateTime?,
    val updatedBy: String?,
    val updatedAt: LocalDateTime?,
) {

    companion object {
        fun from(commercialArea: CommercialAreaModel): CommercialAreaResponse {
            return CommercialAreaResponse(
                code = commercialArea.code,
                areaName = commercialArea.areaName,
                description = commercialArea.description,
                images = commercialArea.images,
                metroAreaCode = commercialArea.metroAreaCode,
                createdBy = commercialArea.createdBy,
                createdAt = commercialArea.createdAt,
                updatedBy = commercialArea.updatedBy,
                updatedAt = commercialArea.updatedAt,
            )
        }
    }

}
