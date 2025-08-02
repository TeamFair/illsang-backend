package com.illsang.management.listener

import com.illsang.common.event.management.area.CommercialAreaExistOrThrowEvent
import com.illsang.common.event.management.area.MetroAreaGetByCommercialAreaEvent
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
    fun getByCommercialArea(event: MetroAreaGetByCommercialAreaEvent) {
        val commercialArea = this.areaService.existOrThrowCommercialArea(event.commercialAreaCode)
        event.response = MetroAreaGetByCommercialAreaEvent.MetroArea(metroAreaCode = commercialArea.metroArea.code)
    }

}
