package com.illsang.common.event.management.area

data class MetroAreaGetByCommercialAreaEvent (
    val commercialAreaCode: String,
) {
    lateinit var response: MetroArea

    class MetroArea(
        val metroAreaCode: String,
    )
}

