package com.illsang.common.event.user.coupon

import com.illsang.common.enums.CouponType

data class CouponInfoGetEvent(
    val couponId: Long
)
{
    lateinit var response: CouponInfo

    class CouponInfo(
        val id: Long,
        val type: CouponType,
        val name: String,
        val imageId: String?,
        val storeId: String,
        val description: String?,
    )
}