package com.illsang.management.listener

import com.illsang.common.event.management.season.SeasonGetCurrentEvent
import com.illsang.management.service.SeasonService
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class SeasonEventListener(
    private val seasonService: SeasonService,
) {

    @EventListener
    fun findCurrentSeason(event: SeasonGetCurrentEvent) {
        val season = this.seasonService.findCurrentSeason(event.currentDate)
        season?.let {
            event.response = SeasonGetCurrentEvent.CurrentSeason(
                seasonId = it.id,
                seasonNumber = season.seasonNumber,
                startDate = season.startDate,
                endDate = season.endDate,
            )
        }
    }

}
