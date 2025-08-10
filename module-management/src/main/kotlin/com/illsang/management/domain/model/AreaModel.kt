package com.illsang.management.domain.model

import com.illsang.management.domain.entity.CommercialAreaEntity
import com.illsang.management.domain.entity.MetroAreaEntity

data class MetroAreaModel(
    val code: String,
    val areaName: String,
    val images: List<String>,
    val commercialAreaModel: List<CommercialAreaModel>,
) {

    companion object {
        fun from(metroArea: MetroAreaEntity): MetroAreaModel {
            return MetroAreaModel(
                code = metroArea.code,
                areaName = metroArea.areaName,
                commercialAreaModel = metroArea.commercialAreas.map { CommercialAreaModel.from(it) },
                images = metroArea.images
            )
        }
    }

}

data class CommercialAreaModel(
    val code: String,
    val areaName: String,
    val description: String,
    val metroAreaCode: String,
    val images: List<String>,
) {

    companion object {
        fun from(commercialArea: CommercialAreaEntity): CommercialAreaModel {
            return CommercialAreaModel(
                code = commercialArea.code,
                areaName = commercialArea.areaName,
                description = commercialArea.description,
                metroAreaCode = commercialArea.metroArea.code,
                images = commercialArea.images,
            )
        }
    }

}
