package com.illsang.quest.domain.model.quset

import com.illsang.common.domain.model.BaseModel
import com.illsang.quest.domain.entity.quest.CouponEntity
import com.illsang.quest.enums.CouponType
import java.time.LocalDateTime

data class CouponModel(
    val id: Long?,
    val type: CouponType,
    val name: String,
    val imageId: String?,
    val password: String?,
    val validFrom: LocalDateTime?,
    val validTo: LocalDateTime?,
    val storeId: String?,
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
            type = entity.type,
            name = entity.name,
            imageId = entity.imageId,
            password = entity.password,
            validFrom = entity.validFrom,
            validTo = entity.validTo,
            storeId = entity.storeId,
            description = entity.description,
            createdBy = entity.createdBy,
            createdAt = entity.createdAt,
            updatedBy = entity.updatedBy,
            updatedAt = entity.updatedAt,
            deleteYn = entity.deleteYn
        )
    }
}
