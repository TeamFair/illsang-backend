package com.illsang.user.scheduler

import com.illsang.common.enums.TitleId
import com.illsang.common.event.management.area.CommercialAreaAllGetEvent
import com.illsang.common.event.management.area.MetroAreaAllGetEvent
import com.illsang.common.event.management.season.SeasonGetCurrentEvent
import com.illsang.user.domain.model.UserRankModel
import com.illsang.user.dto.request.CreateUserTitleRequest
import com.illsang.user.dto.request.UserTitleRequest
import com.illsang.user.service.UserPointService
import com.illsang.user.service.UserTitleService
import org.springframework.context.ApplicationEventPublisher
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId

@Component
class UserTitleScheduler(
    private val userTitleService: UserTitleService,
    private val userPointService: UserPointService,
    private val eventPublisher: ApplicationEventPublisher,
) {
    companion object {
        private val SEOUL_ZONE: ZoneId = ZoneId.of("Asia/Seoul")
    }

    @Scheduled(cron = "0 0 0 * * ?")
    fun assignTitlesOnSeasonEnd() {

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

        val metroGetEvent = MetroAreaAllGetEvent()
        this.eventPublisher.publishEvent(metroGetEvent)
        val metroAreaList = metroGetEvent.response

        metroAreaList.forEach { metroArea ->
            val metroRank = userPointService.findAllRankByUserMetro(currentSeason.seasonId, metroArea.code)
            processRanks(metroRank, userTitleService::mapMetroTitleId)
        }

        val commercialAreaEvent = CommercialAreaAllGetEvent()
        this.eventPublisher.publishEvent(commercialAreaEvent)
        val commercialAreaList = commercialAreaEvent.response

        commercialAreaList.forEach { commercialArea ->
            val commercialRank =
                userPointService.findAllRankByUserCommercial(currentSeason.seasonId, commercialArea.code)
            processRanks(commercialRank, userTitleService::mapMetroTitleId)
        }

        val seasonRank = userPointService.findAllRankByUserContribution(currentSeason.seasonId)
        processRanks(seasonRank, userTitleService::mapContribTitleId)
    }

    private fun isNextDayAfterSeasonEnd(endDate: LocalDateTime): Boolean {
        val endSeoul: LocalDate = endDate.atZone(SEOUL_ZONE).toLocalDate()
        val todaySeoul: LocalDate = LocalDate.now(SEOUL_ZONE)
        return endSeoul.plusDays(1L) == todaySeoul
    }

    // 공통 랭킹 처리: null 안전성과 중복 제거
    private fun processRanks(
        ranks: List<UserRankModel>,
        titleMapper: (Long) -> String?
    ) {
        val requests: List<CreateUserTitleRequest> = ranks.mapNotNull { rankData ->
            val rank = rankData.rank ?: return@mapNotNull null
            val titleId = titleMapper(rank) ?: return@mapNotNull null
            val userId = rankData.user.id ?: return@mapNotNull null
            CreateUserTitleRequest(titleId, userId)
        }

        if (requests.isNotEmpty()) {
            userTitleService.createUserTitles(requests)
        }
    }
}