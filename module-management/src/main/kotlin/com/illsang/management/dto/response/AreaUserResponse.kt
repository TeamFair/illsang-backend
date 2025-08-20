package com.illsang.management.dto.response

import com.illsang.management.domain.model.CommercialAreaModel
import com.illsang.management.domain.model.MetroAreaModel

data class MetroAreaUserResponse(
    val code: String?,
    val areaName: String?,
    val images: List<String>,
    val commercialAreas: List<CommercialAreaUserResponse>,
) {
    companion object {
        fun from(metroArea: MetroAreaModel): MetroAreaUserResponse {
            return MetroAreaUserResponse(
                code = metroArea.code,
                areaName = metroArea.areaName,
                images = metroArea.images,
                commercialAreas = metroArea.commercialAreaModel.map { CommercialAreaUserResponse.from(it) },
            )
        }
    }
}

data class CommercialAreaUserResponse(
    val code: String?,
    val areaName: String?,
    val description: String?,
    val images: List<String>,
    val metroAreaCode: String?,
) {

    companion object {
        fun from(commercialArea: CommercialAreaModel): CommercialAreaUserResponse {
            return CommercialAreaUserResponse(
                code = commercialArea.code,
                areaName = commercialArea.areaName,
                description = commercialArea.description,
                images = commercialArea.images,
                metroAreaCode = commercialArea.metroAreaCode,
            )
        }
    }

}
