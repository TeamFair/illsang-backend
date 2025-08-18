package com.illsang.quest.listener

import com.illsang.common.event.user.coupon.CouponExistOrThrowEvent
import com.illsang.common.event.user.coupon.CouponPasswordVerificationOrThrowEvent
import com.illsang.quest.service.quest.CouponService
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class CouponEventListener(
    private val couponService: CouponService
) {

    @EventListener
    fun handleCouponExistOrThrow(event: CouponExistOrThrowEvent) {
        couponService.validCouponById(event.couponId)
    }

    @EventListener
    fun handleCouponPasswordVerificationOrThrow(event: CouponPasswordVerificationOrThrowEvent){
        couponService.verifyPassword(event.couponId, event.password)
    }

}