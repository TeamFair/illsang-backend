package com.illsang.common.event.management.area

class MetroAreaAllGetEvent()
{
    lateinit var response: List<MetroAllArea>

    class MetroAllArea(
        val code: String,
        val areaName: String,
        val images: List<String>,
    )
}





