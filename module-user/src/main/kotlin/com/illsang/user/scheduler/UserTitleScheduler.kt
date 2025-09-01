package com.illsang.user.scheduler

import com.illsang.common.enums.TitleId
import com.illsang.common.event.management.area.MetroAreaGetEvent
import com.illsang.common.event.management.season.SeasonGetCurrentEvent
import com.illsang.user.service.AreaPointService
import com.illsang.user.service.UserPointService
import com.illsang.user.service.UserTitleService
import org.springframework.context.ApplicationEventPublisher
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId

@RestController
class UserTitleScheduler(
    private val userTitleService: UserTitleService,
    private val userPointService: UserPointService,
    private val areaPointService: AreaPointService,
    private val eventPublisher: ApplicationEventPublisher,
) {

    @Scheduled(cron = "0 0 0 * * ?")
    fun assignUserTitleByRanking() {

        val nowSeoul = LocalDateTime.now(ZoneId.of("Asia/Seoul"))

        // 오늘 기준으로 종료된 시즌을 찾기 위해 '어제' 날짜를 기준으로 시즌 조회
        val targetDateForSeason = nowSeoul.minusDays(1)

        val currentSeasonEvent = SeasonGetCurrentEvent(targetDateForSeason)
        this.eventPublisher.publishEvent(currentSeasonEvent)
        val currentSeason = currentSeasonEvent.response

        // 시즌 종료 익일만 실행
        if (!isNextDayAfterSeasonEnd(currentSeason.endDate)) {
            return
        }


        val metroSeasonRank = areaPointService.findAllRankByMetroArea(currentSeason.seasonId)
        val commercialSeasonRank = areaPointService.findAllRankByCommercialArea(currentSeason.seasonId)

        val seasonRank = userPointService.findAllRankByUserContribution(currentSeason.seasonId)

        seasonRank.forEach { rankData ->
            val titleId = when {
                rankData.rank == 1L -> TitleId.TITLE_CONTRIB_1.titleId
                rankData.rank == 2L -> TitleId.TITLE_CONTRIB_2.titleId
                rankData.rank == 3L -> TitleId.TITLE_CONTRIB_3.titleId
                rankData.rank!! <= 10L -> TitleId.TITLE_CONTRIB_4_TO_10.titleId
                else -> return@forEach
            }

            userTitleService.createUserTitle(rankData.user.id!!, titleId)
        }


    }

    private fun isNextDayAfterSeasonEnd(endDate: LocalDateTime): Boolean {
        val endSeoul: LocalDate = endDate.atZone(ZoneId.of("Asia/Seoul")).toLocalDate()
        val todaySeoul: LocalDate = LocalDate.now(ZoneId.of("Asia/Seoul"))
        return endSeoul.plusDays(1L).equals(todaySeoul)
    }


}