package com.illsang.quest.domain.model.quset

import com.illsang.common.domain.model.BaseModel
import com.illsang.quest.domain.entity.quest.CouponEntity
import com.illsang.common.enums.CouponType
import java.time.LocalDateTime

data class CouponModel(
    val id: Long?,
    val name: String,
    val imageId: String?,
    val password: String?,
    val validFrom: LocalDateTime?,
    val validTo: LocalDateTime?,
    val store: StoreModel? = null,
    val description: String?,
    val createdBy: String?,
    val createdAt: LocalDateTime?,
    val updatedBy: String?,
    val updatedAt: LocalDateTime?,
    val deleteYn: Boolean
) {
    companion object {
        fun from(entity: CouponEntity) = CouponModel(
            id = entity.id,
            name = entity.name,
            imageId = entity.imageId,
            password = entity.password,
            validFrom = entity.validFrom,
            validTo = entity.validTo,
            store = entity.store?.let { StoreModel.from(it) },
            description = entity.description,
            createdBy = entity.createdBy,
            createdAt = entity.createdAt,
            updatedBy = entity.updatedBy,
            updatedAt = entity.updatedAt,
            deleteYn = entity.deleteYn,
        )
    }
}
