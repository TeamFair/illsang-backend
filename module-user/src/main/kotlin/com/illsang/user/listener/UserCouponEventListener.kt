package com.illsang.user.listener

import com.illsang.common.event.user.coupon.UserCouponCreateEvent
import com.illsang.user.dto.request.UserCouponCreateRequest
import com.illsang.user.service.UserCouponService
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class UserCouponEventListener(
    private val userCouponService: UserCouponService,
) {

    @EventListener
    fun createCoupon(event: UserCouponCreateEvent) {
        val request = event.couponId?.let {
            UserCouponCreateRequest(
                userId = event.userId,
                couponId = it,
            )
        }

        request?.let { userCouponService.create(it) }
    }
}