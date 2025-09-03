package com.illsang.common.event.user.coupon

data class CouponInfoGetEvent(
    val couponId: Long
)
{
    lateinit var response: CouponInfo
    class CouponInfo(
        val name: String,
        val storeId: String,
    )
}