package com.illsang.quest.dto.response.quest

import com.illsang.common.enums.CouponType
import com.illsang.quest.domain.model.quset.CouponSettingModel

data class CouponSettingResponse(
    val id: Long,
    val type: CouponType,
    val amount: Int? = null,
    val coupon: CouponResponse? = null,
) {
    companion object{
        fun from(model: CouponSettingModel) = CouponSettingResponse(
            id = model.id,
            type = model.type,
            coupon = model.coupon?.let { CouponResponse.from(it) },
            amount = model.amount,
        )
    }
}