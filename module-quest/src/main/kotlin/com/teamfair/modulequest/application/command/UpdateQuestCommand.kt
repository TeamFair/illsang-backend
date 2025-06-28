package com.teamfair.modulequest.application.command

data class UpdateQuestCommand(
    val id: Long,
    val imageId: Long? = null,
    val writerName: String? = null,
    val mainImageId: Long? = null,
    val popularYn: Boolean? = null,
    val type: String? = null,
    val repeatFrequency: String? = null,
    val sortOrder: Int? = null
) 