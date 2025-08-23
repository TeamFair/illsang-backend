package com.illsang.management.repository

import com.illsang.management.domain.entity.SeasonEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.LocalDateTime
import java.util.Optional

interface SeasonRepository : JpaRepository<SeasonEntity, Long> {
    fun findBySeasonNumber(seasonNumber: Int): SeasonEntity?
    fun findBySeasonNumberAndIdNot(seasonNumber: Int, id: Long): SeasonEntity?
    fun findByStartDateLessThanEqualAndEndDateGreaterThanEqual(
        startDate: LocalDateTime,
        endDate: LocalDateTime
    ): SeasonEntity?
    fun findByStartDateLessThanEqualAndEndDateGreaterThanEqualAndIdNot(
        startDate: LocalDateTime,
        endDate: LocalDateTime,
        id: Long,
    ): SeasonEntity?

    @Query("SELECT e FROM SeasonEntity e WHERE :currentDate BETWEEN e.startDate AND e.endDate")
    fun findByCurrentDate(currentDate: LocalDateTime): SeasonEntity?

}
