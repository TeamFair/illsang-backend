package com.illsang.management.listener

import com.illsang.common.event.management.area.CommercialAreaAllGetEvent
import com.illsang.common.event.management.area.CommercialAreaByMetroAreaGetEvent
import com.illsang.common.event.management.area.CommercialAreaExistOrThrowEvent
import com.illsang.common.event.management.area.CommercialAreaGetEvent
import com.illsang.common.event.management.area.MetroAreaAllGetEvent
import com.illsang.common.event.management.area.MetroAreaGetByCommercialAreaEvent
import com.illsang.common.event.management.area.MetroAreaGetEvent
import com.illsang.management.service.AreaCommercialService
import com.illsang.management.service.AreaMetroService
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class AreaEventListener(
    private val commercialService: AreaCommercialService,
    private val metroService: AreaMetroService,
) {

    @EventListener
    fun existOrThrowArea(event: CommercialAreaExistOrThrowEvent) {
        this.commercialService.existOrThrowCommercialArea(event.commercialAreaCode)
    }

    @EventListener
    fun getMetroByCommercialArea(event: MetroAreaGetByCommercialAreaEvent) {
        val commercialArea = this.commercialService.existOrThrowCommercialArea(event.commercialAreaCode)
        event.response = MetroAreaGetByCommercialAreaEvent.MetroArea(metroAreaCode = commercialArea.metroArea.code)
    }

    @EventListener
    fun getMetroAreaInfo(event: MetroAreaGetEvent) {
        val metros = this.metroService.findByMetroCodes(event.metroAreaCodes)

        event.response = metros.map {
            MetroAreaGetEvent.MetroArea(
                code = it.code,
                areaName = it.areaName,
                images = it.images,
            )
        }
    }

    @EventListener
    fun getCommercialAreaInfo(event: CommercialAreaGetEvent) {
        val commercials = this.commercialService.findByCommercialCodes(event.commercialAreaCodes)

        event.response = commercials.map {
            CommercialAreaGetEvent.CommercialArea(
                code = it.code,
                areaName = it.areaName,
                images = it.images,
            )
        }
    }

    @EventListener
    fun getMetroAreaAllInfo(event: MetroAreaAllGetEvent){
        val metros = this.metroService.findAllMetro()
        event.response = metros.map {
            MetroAreaAllGetEvent.MetroAllArea(
                code = it.code,
                areaName = it.areaName,
                images = it.images,
            )
        }
    }

    @EventListener
    fun getCommercialAreaAllInfo(event: CommercialAreaAllGetEvent){
        val commercials = this.commercialService.findAllCommercial()
        event.response = commercials.map {
            CommercialAreaAllGetEvent.CommercialAllArea(
                code = it.code,
                areaName = it.areaName,
                images = it.images,
            )
        }
    }

    @EventListener
    fun getCommercialAreaByMetro(event: CommercialAreaByMetroAreaGetEvent){
        val commercials = this.commercialService.findCommercialByMetro(event.metroCode)
        event.response = commercials.map{
            CommercialAreaGetEvent.CommercialArea(
                code = it.code,
                areaName = it.areaName,
                images = it.images,
            )
        }
    }

}
