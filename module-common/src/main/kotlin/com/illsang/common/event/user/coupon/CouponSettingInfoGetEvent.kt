package com.illsang.common.event.user.coupon

import com.illsang.common.enums.CouponType

class CouponSettingInfoGetEvent {

    lateinit var response: List<CouponSettingInfo>

    class CouponSettingInfo(
        val id: Long?,
        val couponId: Long?,
        val type: CouponType?,
        val amount: Int?,
        val coupons: CouponInfoGetEvent.CouponInfo?
    )
}