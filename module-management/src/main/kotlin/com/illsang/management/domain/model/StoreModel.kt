package com.illsang.management.domain.model

import com.illsang.common.domain.model.BaseModel
import com.illsang.management.domain.entity.StoreEntity
import java.time.LocalDateTime

data class StoreModel(
    val id: Long,
    val imageId: String?,
    val name: String,
    val managerId: String,
    val description: String?,
    val phoneNumber: String?,
    val address: String?,
    val activeYn: Boolean?,
    val commercialAreaCode: String?,
    val metroAreaCode: String?,
    override val createdBy: String? = null,
    override val createdAt: LocalDateTime? = null,
    override val updatedBy: String? = null,
    override val updatedAt: LocalDateTime? = null,
) : BaseModel(createdBy, createdAt, updatedBy, updatedAt) {
    companion object {
        fun from(entity: StoreEntity): StoreModel = StoreModel(
            id = entity.id!!,
            imageId = entity.imageId,
            name = entity.name,
            managerId = entity.managerId,
            description = entity.description,
            phoneNumber = entity.phoneNumber,
            address = entity.address,
            activeYn = entity.activeYn,
            commercialAreaCode = entity.commercialAreaCode,
            metroAreaCode = entity.metroAreaCode,
            createdBy = entity.createdBy,
            createdAt = entity.createdAt,
            updatedBy = entity.updatedBy,
            updatedAt = entity.updatedAt,
        )
    }
}