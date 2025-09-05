package com.illsang.quest.dto.response.quest

import com.illsang.quest.domain.model.quset.StoreModel

data class StoreResponse(
    val id: Long,
    val imageId: String?,
    val name: String,
    val managerId: String,
    val description: String?,
    val phoneNumber: String?,
    val address: String?,
    val activeYn: Boolean?
) {
    companion object {
        fun from(model: StoreModel) = StoreResponse(
            id = model.id,
            imageId = model.imageId,
            name = model.name,
            managerId = model.managerId,
            description = model.description,
            phoneNumber = model.phoneNumber,
            address = model.address,
            activeYn = model.activeYn
        )
    }
}