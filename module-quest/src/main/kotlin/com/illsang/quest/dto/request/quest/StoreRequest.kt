package com.illsang.quest.dto.request.quest

import com.illsang.quest.domain.entity.quest.StoreEntity

data class StoreCreateRequest(
    val imageId: String,
    val name: String,
    val managerId: String,
    val description: String? = null,
    val phoneNumber: String? = null,
    val address: String? = null,
    val activeYn: Boolean? = null,
) {
    fun toEntity(): StoreEntity {
        return StoreEntity(
            imageId = imageId,
            name = name,
            managerId = managerId,
            description = description,
            phoneNumber = phoneNumber,
            address = address,
            activeYn = activeYn ?: true,
        )
    }
}

data class StoreUpdateRequest(
    val name: String,
    val managerId: String,
    val description: String? = null,
    val phoneNumber: String? = null,
    val address: String? = null,
    val activeYn: Boolean? = null,
    val imageId: String? = null,
) {
    fun toEntity(id: Long): StoreEntity {
        return StoreEntity(
            id = id,
            imageId = imageId,
            name = name,
            managerId = managerId,
            description = description,
            phoneNumber = phoneNumber,
            address = address,
            activeYn = activeYn ?: true,
        )
    }
}