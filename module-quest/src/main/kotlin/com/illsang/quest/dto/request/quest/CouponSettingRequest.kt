package com.illsang.quest.dto.request.quest

import com.illsang.common.enums.CouponType
import com.illsang.quest.domain.entity.quest.CouponEntity
import com.illsang.quest.domain.entity.quest.CouponSettingEntity

data class CouponSettingCreateRequest(
    val couponId: Long,
    val type: CouponType,
    val amount: Int? = null,

){
    fun toEntity(coupon: CouponEntity): CouponSettingEntity {
        return CouponSettingEntity(
            coupon = coupon,
            type = this.type,
            amount = this.amount,
        )
    }
}

data class CouponSettingUpdateRequest(
    val type: CouponType,
    val amount: Int? = null,
    val couponId: Long,
)