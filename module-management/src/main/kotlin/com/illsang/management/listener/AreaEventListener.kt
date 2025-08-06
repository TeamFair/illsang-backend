package com.illsang.management.listener

import com.illsang.common.event.management.area.CommercialAreaExistOrThrowEvent
import com.illsang.common.event.management.area.CommercialAreaGetEvent
import com.illsang.common.event.management.area.MetroAreaGetByCommercialAreaEvent
import com.illsang.common.event.management.area.MetroAreaGetEvent
import com.illsang.management.service.AreaService
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class AreaEventListener(
    private val areaService: AreaService,
) {

    @EventListener
    fun existOrThrowArea(event: CommercialAreaExistOrThrowEvent) {
        this.areaService.existOrThrowCommercialArea(event.commercialAreaCode)
    }

    @EventListener
    fun getMetroByCommercialArea(event: MetroAreaGetByCommercialAreaEvent) {
        val commercialArea = this.areaService.existOrThrowCommercialArea(event.commercialAreaCode)
        event.response = MetroAreaGetByCommercialAreaEvent.MetroArea(metroAreaCode = commercialArea.metroArea.code)
    }

    @EventListener
    fun getMetroAreaInfo(event: MetroAreaGetEvent) {
        val metros = this.areaService.findByMetroCodes(event.metroAreaCodes)

        event.response = metros.map {
            MetroAreaGetEvent.MetroArea(
                code = it.code,
                areaName = it.areaName,
            )
        }
    }

    @EventListener
    fun getCommercialAreaInfo(event: CommercialAreaGetEvent) {
        val commercials = this.areaService.findByCommercialCodes(event.commercialAreaCodes)

        event.response = commercials.map {
            CommercialAreaGetEvent.CommercialArea(
                code = it.code,
                areaName = it.areaName,
            )
        }
    }

}
