package com.illsang.management.dto.response

import com.illsang.management.domain.model.CommercialAreaModel
import com.illsang.management.domain.model.MetroAreaModel

data class MetroAreaResponse(
    val code: String?,
    val areaName: String?,
    val commercialAreaModel: List<CommercialAreaResponse>,
) {
    companion object {
        fun from(metroArea: MetroAreaModel): MetroAreaResponse {
            return MetroAreaResponse(
                code = metroArea.code,
                areaName = metroArea.areaName,
                commercialAreaModel = metroArea.commercialAreaModel.map { CommercialAreaResponse.from(it) },
            )
        }
    }
}

data class CommercialAreaResponse(
    val code: String?,
    val areaName: String?,
    val description: String?,
    val metroAreaCode: String?,
) {

    companion object {
        fun from(commercialArea: CommercialAreaModel): CommercialAreaResponse {
            return CommercialAreaResponse(
                code = commercialArea.code,
                areaName = commercialArea.areaName,
                description = commercialArea.description,
                metroAreaCode = commercialArea.metroAreaCode,
            )
        }
    }

}
