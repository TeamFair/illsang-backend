package com.illsang.common.event.user.coupon

import java.time.LocalDateTime

data class CouponInfoGetEvent(
    val couponId: Long
)
{
    lateinit var response: CouponInfo

    class CouponInfo(
        val id: Long?,
        val name: String?,
        val imageId: String?,
        val storeId: Long?,
        val description: String?,
        val validFrom: LocalDateTime?,
        val validTo: LocalDateTime?,
    )
}