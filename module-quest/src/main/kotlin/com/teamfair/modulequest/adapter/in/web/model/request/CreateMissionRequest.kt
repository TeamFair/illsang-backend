package com.teamfair.modulequest.adapter.`in`.web.model.request

data class CreateMissionRequest(
    val type: String,
    val title: String,
    val sortOrder: Int = 0,
    val questId: Long
) 