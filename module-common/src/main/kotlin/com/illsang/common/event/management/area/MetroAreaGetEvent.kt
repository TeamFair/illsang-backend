package com.illsang.common.event.management.area

data class MetroAreaGetEvent (
    val metroAreaCodes: List<String>,
) {
    lateinit var response: List<MetroArea>

    class MetroArea(
        val code: String,
        val areaName: String,
    )
}

