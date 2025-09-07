package com.illsang.management.dto.request

import com.illsang.management.domain.entity.StoreEntity

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
        val store = StoreEntity(
            imageId = imageId,
            name = name,
            managerId = managerId,
            description = description,
            phoneNumber = phoneNumber,
            address = address,
            activeYn = activeYn ?: true,
        )
        return store
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
        val store = StoreEntity(
            id = id,
            imageId = imageId,
            name = name,
            managerId = managerId,
            description = description,
            phoneNumber = phoneNumber,
            address = address,
            activeYn = activeYn ?: true,
        )

        return store
    }
}