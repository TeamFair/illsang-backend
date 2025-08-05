package com.illsang.management.dto.response

import com.illsang.management.domain.model.SeasonModel
import java.time.LocalDateTime

data class SeasonResponse(
    val id: Long?,
    val seasonNumber: Int?,
    val startDate: LocalDateTime?,
    val endDate: LocalDateTime?,
) {
    companion object {
        fun from(season: SeasonModel): SeasonResponse {
            return SeasonResponse(
                id = season.id,
                seasonNumber = season.seasonNumber,
                startDate = season.startDate,
                endDate = season.endDate,
            )
        }
    }
}
