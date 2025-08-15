package com.illsang.quest.domain.model.quset

import com.illsang.common.domain.model.BaseModel
import com.illsang.quest.domain.entity.quest.CouponEntity
import java.time.LocalDateTime

data class CouponModel(
    var id: Long? = null,
    override val createdBy: String? = null,
    override val createdAt: LocalDateTime? = null,
    override val updatedBy: String? = null,
    override val updatedAt: LocalDateTime? = null
) : BaseModel(createdBy, createdAt, updatedBy, updatedAt) {

    companion object {
        fun from(coupon: CouponEntity): CouponModel {
            return CouponModel(
                createdBy = coupon.createdBy,
                createdAt = coupon.createdAt,
                updatedBy = coupon.updatedBy,
                updatedAt = coupon.updatedAt,
            );
        }
    }
}