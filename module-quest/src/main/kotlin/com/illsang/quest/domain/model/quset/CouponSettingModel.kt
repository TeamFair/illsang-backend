package com.illsang.quest.domain.model.quset

import com.illsang.common.domain.model.BaseModel
import com.illsang.common.enums.CouponType
import com.illsang.quest.domain.entity.quest.CouponSettingEntity
import java.time.LocalDateTime

data class CouponSettingModel(
    val id: Long,
    val type: CouponType,
    val amount: Int? = null,
    val coupon: CouponModel? = null,
    override val createdBy: String?,
    override val createdAt: LocalDateTime?,
    override val updatedBy: String?,
    override val updatedAt: LocalDateTime?,
) : BaseModel(createdBy, createdAt, updatedBy, updatedAt) {
    companion object{
        fun from(entity: CouponSettingEntity) = CouponSettingModel(
            id = entity.id!!,
            type = entity.type,
            amount = entity.amount,
            coupon = entity.coupon?.let { CouponModel.from(it) },
            createdBy = entity.createdBy,
            createdAt = entity.createdAt,
            updatedBy = entity.updatedBy,
            updatedAt = entity.updatedAt,
        )
    }
}