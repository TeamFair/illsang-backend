package com.illsang.management.dto.request

import com.illsang.management.domain.entity.SeasonEntity
import java.time.LocalDateTime

data class SeasonCreateRequest (
    val seasonNumber: Int,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
) {
    fun toEntity(): SeasonEntity {
        return SeasonEntity(
            seasonNumber = this.seasonNumber,
            startDate = this.startDate,
            endDate = endDate,
        )
    }
}

data class SeasonUpdateRequest (
    val seasonNumber: Int,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
)
