package com.illsang.quest.listener

import com.illsang.common.event.user.coupon.CouponInfoGetEvent
import com.illsang.common.event.user.coupon.CouponSettingInfoGetEvent
import com.illsang.quest.service.quest.CouponSettingService
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class CouponSettingEventListener(
    private val couponSettingService: CouponSettingService,
) {

    @EventListener
    fun getCouponSettingInfo(event: CouponSettingInfoGetEvent) {
        val couponSettings = couponSettingService.getAll()

        event.response = couponSettings.map { setting ->
            val coupon = setting.coupon

            CouponSettingInfoGetEvent.CouponSettingInfo(
                id = setting.id,
                couponId = coupon?.id,
                type = setting.type,
                amount = setting.amount,
                coupons = coupon?.let {
                    CouponInfoGetEvent.CouponInfo(
                        id = it.id,
                        name = it.name,
                        imageId = it.imageId,
                        validFrom = it.validFrom,
                        validTo = it.validTo,
                        description = it.description,
                        storeId = it.storeId
                    )
                }
            )
        }
    }
}