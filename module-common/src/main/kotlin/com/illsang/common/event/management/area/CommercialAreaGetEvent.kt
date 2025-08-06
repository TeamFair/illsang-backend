package com.illsang.common.event.management.area

data class CommercialAreaGetEvent (
    val commercialAreaCodes: List<String>,
) {
    lateinit var response: List<CommercialArea>

    class CommercialArea(
        val code: String,
        val areaName: String,
    )
}

