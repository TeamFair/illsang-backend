package com.illsang.common.event.management.area

data class CommercialAreaByMetroAreaGetEvent(
    val metroCode: String?
) {
    lateinit var response: List<CommercialAreaGetEvent.CommercialArea>
}