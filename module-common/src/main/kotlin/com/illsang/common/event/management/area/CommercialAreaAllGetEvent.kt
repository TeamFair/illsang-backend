package com.illsang.common.event.management.area

class CommercialAreaAllGetEvent() {
    lateinit var response: List<CommercialAllArea>

    class CommercialAllArea(
        val code: String,
        val areaName: String,
        val images: List<String>,
    )
}