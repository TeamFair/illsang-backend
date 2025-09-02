package com.illsang.user.scheduler

import com.illsang.common.enums.TitleId
import com.illsang.common.event.management.area.CommercialAreaAllGetEvent
import com.illsang.common.event.management.area.MetroAreaAllGetEvent
import com.illsang.common.event.management.season.SeasonGetCurrentEvent
import com.illsang.user.domain.model.UserRankModel
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
            processRanks(metroRank, ::mapMetroTitleId)
        }

        val commercialAreaEvent = CommercialAreaAllGetEvent()
        this.eventPublisher.publishEvent(commercialAreaEvent)
        val commercialAreaList = commercialAreaEvent.response

        commercialAreaList.forEach { commercialArea ->
            val commercialRank =
                userPointService.findAllRankByUserCommercial(currentSeason.seasonId, commercialArea.code)
            processRanks(commercialRank, ::mapMetroTitleId)
        }

        val seasonRank = userPointService.findAllRankByUserContribution(currentSeason.seasonId)
        processRanks(seasonRank, ::mapContribTitleId)
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
        ranks.forEach { rankData ->
            val rank: Long = rankData.rank ?: return@forEach
            val titleId: String = titleMapper(rank) ?: return@forEach
            val userId: String = rankData.user.id ?: return@forEach
            userTitleService.createUserTitle(userId, titleId)
        }
    }

    // 메트로/상권 랭킹 -> 타이틀 매핑
    private fun mapMetroTitleId(rank: Long): String? = when {
        rank == 1L -> TitleId.TITLE_METRO_1.titleId
        rank == 2L -> TitleId.TITLE_METRO_2.titleId
        rank == 3L -> TitleId.TITLE_METRO_3.titleId
        rank in 4L..10L -> TitleId.TITLE_METRO_4_TO_10.titleId
        else -> null
    }

    // 시즌 기여 랭킹 -> 타이틀 매핑
    private fun mapContribTitleId(rank: Long): String? = when {
        rank == 1L -> TitleId.TITLE_CONTRIB_1.titleId
        rank == 2L -> TitleId.TITLE_CONTRIB_2.titleId
        rank == 3L -> TitleId.TITLE_CONTRIB_3.titleId
        rank in 4L..10L -> TitleId.TITLE_CONTRIB_4_TO_10.titleId
        else -> null
    }


}