package com.illsang.quest.domain.model.quset

import com.illsang.common.domain.model.BaseModel
import com.illsang.quest.domain.entity.quest.CouponEntity
import java.time.LocalDateTime

data class CouponModel(
    val id: Long?,
    val name: String,
    val imageId: String?,
    val password: String?,
    val validFrom: LocalDateTime?,
    val validTo: LocalDateTime?,
    val storeId: Long? = null,
    val description: String?,
    val deleteYn: Boolean,
    override val createdBy: String?,
    override val createdAt: LocalDateTime?,
    override val updatedBy: String?,
    override val updatedAt: LocalDateTime?,
) : BaseModel(createdBy, createdAt, updatedBy, updatedAt)  {
    companion object {
        fun from(entity: CouponEntity) = CouponModel(
            id = entity.id,
            name = entity.name,
            imageId = entity.imageId,
            password = entity.password,
            validFrom = entity.validFrom,
            validTo = entity.validTo,
            storeId = entity.storeId,
            description = entity.description,
            deleteYn = entity.deleteYn,
            createdBy = entity.createdBy,
            createdAt = entity.createdAt,
            updatedBy = entity.updatedBy,
            updatedAt = entity.updatedAt,
        )
    }
}
