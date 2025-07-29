package com.illsang.management.listener

import com.illsang.common.event.management.area.CommercialAreaExistOrThrowEvent
import com.illsang.common.event.management.season.CurrentSeason
import com.illsang.common.event.management.season.SeasonGetCurrentEvent
import com.illsang.management.service.AreaService
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
        event.response = CurrentSeason(
            seasonNumber = season.seasonNumber,
            startDate = season.startDate,
            endDate = season.endDate,
        )
    }

}
