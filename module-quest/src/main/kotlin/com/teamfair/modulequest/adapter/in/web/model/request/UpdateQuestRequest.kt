package com.teamfair.modulequest.adapter.`in`.web.model.request

data class UpdateQuestRequest(
    val imageId: Long? = null,
    val writerName: String? = null,
    val mainImageId: Long? = null,
    val popularYn: Boolean? = null,
    val type: String? = null,
    val repeatFrequency: String? = null,
    val sortOrder: Int? = null
) 