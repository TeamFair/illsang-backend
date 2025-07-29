package com.illsang.management.domain.model

import com.illsang.common.domain.model.BaseModel
import com.illsang.management.domain.entity.SeasonEntity
import java.time.LocalDateTime

data class SeasonModel(
    val id: Long,
    val seasonNumber: Int,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
    override val createdBy: String? = null,
    override val createdAt: LocalDateTime? = null,
    override val updatedBy: String? = null,
    override val updatedAt: LocalDateTime? = null
) : BaseModel(createdBy, createdAt, updatedBy, updatedAt) {

    companion object {
        fun from(season: SeasonEntity): SeasonModel {
            return SeasonModel(
                id = season.id!!,
                seasonNumber = season.seasonNumber,
                startDate = season.startDate,
                endDate = season.endDate,
                createdBy = season.createdBy,
                createdAt = season.createdAt,
                updatedBy = season.updatedBy,
                updatedAt = season.updatedAt,
            )
        }
    }

}
