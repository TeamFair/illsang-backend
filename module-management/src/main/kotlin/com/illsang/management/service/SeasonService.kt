package com.illsang.management.service

import com.illsang.management.domain.entity.SeasonEntity
import com.illsang.management.domain.model.SeasonModel
import com.illsang.management.dto.request.SeasonCreateRequest
import com.illsang.management.dto.request.SeasonUpdateRequest
import com.illsang.management.repository.SeasonRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional(readOnly = true)
class SeasonService(
    private val seasonRepository: SeasonRepository,
) {

    fun findAll(): List<SeasonModel> {
        val seasons = this.seasonRepository.findAll()

        return seasons.map(SeasonModel::from)
    }

    @Transactional
    fun createSeason(request: SeasonCreateRequest): SeasonModel {
        if (this.seasonRepository.findBySeasonNumber(request.seasonNumber) != null) {
            throw IllegalArgumentException("Season already exists")
        }
        if (this.seasonRepository.findByStartDateLessThanEqualAndEndDateGreaterThanEqual(
                request.endDate,
                request.startDate
            ) != null
        ) {
            throw IllegalArgumentException("This time slot overlaps with another Season")
        }

        val season = this.seasonRepository.save(request.toEntity())

        return SeasonModel.from(season)
    }

    @Transactional
    fun updateSeason(id: Long, request: SeasonUpdateRequest): SeasonModel {
        val season = this.findById(id)

        this.seasonRepository.findBySeasonNumberAndIdNot(request.seasonNumber, id)
            ?: throw IllegalArgumentException("Season already exists")
        this.seasonRepository.findByStartDateLessThanEqualAndEndDateGreaterThanEqualAndIdNot(
            request.endDate, request.startDate, id,
        ) ?: throw IllegalArgumentException("This time slot overlaps with another Season")

        season.update(request)

        return SeasonModel.from(season)
    }

    @Transactional
    fun deleteSeason(id: Long) {
        val season = this.findById(id)

        this.seasonRepository.delete(season)
    }

    fun findCurrentSeason(currentDate: LocalDateTime): SeasonModel? {

        val season = seasonRepository.findByCurrentDate(currentDate)
            ?: throw IllegalArgumentException("현재 진행중인 시즌이 없습니다.")

        return SeasonModel.from(season)
    }

    private fun findById(id: Long): SeasonEntity =
        this.seasonRepository.findByIdOrNull(id)
            ?: throw IllegalArgumentException("Season not found with id: $id")

}
