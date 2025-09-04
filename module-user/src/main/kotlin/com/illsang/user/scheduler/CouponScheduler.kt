package com.illsang.user.scheduler

import com.illsang.user.service.UserCouponService
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class CouponScheduler(
    private val userCouponService: UserCouponService,
) {

    @Scheduled(cron = "0 0 0 * * ?")
    fun issueUserCoupon(){

    }
}