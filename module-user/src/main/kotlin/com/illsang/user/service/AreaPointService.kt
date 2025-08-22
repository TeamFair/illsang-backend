package com.illsang.user.service

import com.illsang.common.enums.PointType
import com.illsang.common.event.management.area.CommercialAreaGetEvent
import com.illsang.common.event.management.area.MetroAreaGetEvent
import com.illsang.user.dto.response.CommercialRankResponse
import com.illsang.user.dto.response.MetroRankResponse
import com.illsang.user.repository.UserPointRepository
import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
@Transactional(readOnly = true)
class AreaPointService(
    private val userPointRepository: UserPointRepository,
    private val eventPublisher: ApplicationEventPublisher,
) {

    fun findAllRankByMetroArea(seasonId: Long?): List<MetroRankResponse> {
        val metroRank = this.userPointRepository.findAllAreaRank(seasonId, PointType.METRO, Pageable.ofSize(30))

        val metroEvent = MetroAreaGetEvent(metroRank.content.mapNotNull { it?.first })
        this.eventPublisher.publishEvent(metroEvent)

        return metroRank.content.mapIndexed { index, it ->
            MetroRankResponse(
                metroCode = it?.first!!,
                point = it.second,
                rank = index + 1,
                areaName = metroEvent.response.find { metro -> metro.code == it.first }!!.areaName,
                images = metroEvent.response.find { metro -> metro.code == it.first }!!.images
            )
        }
    }

    fun findAllRankByCommercialArea(seasonId: Long?): List<CommercialRankResponse> {
        val commercialRank =
            this.userPointRepository.findAllAreaRank(seasonId, PointType.COMMERCIAL, Pageable.ofSize(30))

        val commercialEvent = CommercialAreaGetEvent(commercialRank.content.mapNotNull { it?.first })
        this.eventPublisher.publishEvent(commercialEvent)

        return commercialRank.content.mapIndexed { index, it ->
        CommercialRankResponse(
                commercialCode = it?.first!!,
                point = it.second,
                rank = index + 1,
                areaName = commercialEvent.response.find { metro -> metro.code == it.first }!!.areaName,
                images = commercialEvent.response.find { metro -> metro.code == it.first }!!.images,
            )
        }
    }

}
