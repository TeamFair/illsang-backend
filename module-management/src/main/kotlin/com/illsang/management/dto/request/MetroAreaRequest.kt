package com.illsang.management.dto.request

import com.illsang.management.domain.entity.CommercialAreaEntity
import com.illsang.management.domain.entity.MetroAreaEntity

data class MetroCreateRequest(
    val code: String,
    val areaName: String,
    val images: List<String>?,
) {
    fun toEntity(): MetroAreaEntity {
        return MetroAreaEntity(
            code = code,
            areaName = areaName,
            images = images?.toMutableList() ?: mutableListOf(),
        )
    }
}

data class MetroUpdateRequest(
    val code: String,
    val areaName: String,
    val images: List<String>?,
)

data class CommercialCreateRequest(
    val code: String,
    val areaName: String,
    val description: String?,
    val images: List<String>?,
    val metroAreaCode: String,
) {
    fun toEntity(metro: MetroAreaEntity): CommercialAreaEntity {
        return CommercialAreaEntity(
            code = code,
            areaName = areaName,
            description = description,
            images = images?.toMutableList() ?: mutableListOf(),
            metroArea = metro,
        )
    }
}

data class CommercialUpdateRequest(
    val code: String,
    val areaName: String,
    val description: String?,
    val images: List<String>?,
)
