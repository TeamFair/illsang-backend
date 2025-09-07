package com.illsang.user.scheduler

import com.illsang.common.enums.CouponType
import com.illsang.common.event.management.season.SeasonGetCurrentEvent
import com.illsang.user.service.UserCouponService
import org.springframework.context.ApplicationEventPublisher
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId


@Component
class CouponScheduler(
    private val userCouponService: UserCouponService,
    private val eventPublisher: ApplicationEventPublisher,
) {

    companion object {
        private val SEOUL_ZONE: ZoneId = ZoneId.of("Asia/Seoul")
    }

    @Scheduled(cron = "0 0 0 * * MON")
    fun issueWeeklyCoupons() {
        userCouponService.issueCoupons(CouponType.WEEK)
    }

    @Scheduled(cron = "0 0 0 1 * *")
    fun issueMonthlyCoupons() {
        userCouponService.issueCoupons(CouponType.MONTH)
    }

    @Scheduled(cron = "0 0 0 * * ?")
    fun issueSeasonCoupons() {
        val nowSeoul = LocalDateTime.now(SEOUL_ZONE)
        // 오늘 기준으로 종료된 시즌을 찾기 위해 '어제' 날짜를 기준으로 시즌 조회
        val targetDateForSeason = nowSeoul.minusDays(1)
        val currentSeasonEvent = SeasonGetCurrentEvent(targetDateForSeason)
        this.eventPublisher.publishEvent(currentSeasonEvent)
        val currentSeason = currentSeasonEvent.response
        // 시즌 종료 익일만 실행
        if (!isNextDayAfterSeasonEnd(currentSeason.endDate)) {
            return
        }
        userCouponService.issueCoupons(CouponType.SEASON)
    }

    private fun isNextDayAfterSeasonEnd(endDate: LocalDateTime): Boolean {
        val endSeoul: LocalDate = endDate.atZone(SEOUL_ZONE).toLocalDate()
        val todaySeoul: LocalDate = LocalDate.now(SEOUL_ZONE)
        return endSeoul.plusDays(1L) == todaySeoul
    }

}