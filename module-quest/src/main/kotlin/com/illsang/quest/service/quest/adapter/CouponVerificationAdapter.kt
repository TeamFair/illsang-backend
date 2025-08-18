package com.illsang.quest.service.quest.adapter

import com.illsang.common.port.coupon.CouponVerificationPort
import com.illsang.quest.service.quest.CouponService
import org.springframework.stereotype.Service

@Service
class CouponVerificationAdapter(
    private val couponService: CouponService
) : CouponVerificationPort {

    override fun verifyPassword(couponId: Long, password: String): Boolean {
        return couponService.verifyPassword(couponId, password)
    }

}
