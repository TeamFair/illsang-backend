package com.illsang.management.domain.entity

import com.illsang.common.entity.BaseEntity
import com.illsang.management.dto.request.SeasonUpdateRequest
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "season")
class SeasonEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "season_number")
    var seasonNumber: Int,

    @Column(name = "start_date")
    var startDate: LocalDateTime,

    @Column(name = "end_date")
    var endDate: LocalDateTime,
) : BaseEntity() {

    init {
        validateDate()
    }

    fun update(request: SeasonUpdateRequest) {
        this.seasonNumber = request.seasonNumber
        this.startDate = request.startDate
        this.endDate = request.endDate

        validateDate()
    }

    private fun validateDate() {
        if (!endDate.isAfter(startDate)) {
            throw IllegalArgumentException("endDate must be after $startDate")
        }
    }

}
