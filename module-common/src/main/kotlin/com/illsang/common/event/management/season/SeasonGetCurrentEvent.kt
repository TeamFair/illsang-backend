package com.illsang.common.event.management.season

import java.time.LocalDateTime

data class SeasonGetCurrentEvent (
    val currentDate: LocalDateTime,
) {
    lateinit var response: CurrentSeason
}

class CurrentSeason(
    val seasonNumber: Int,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
)
