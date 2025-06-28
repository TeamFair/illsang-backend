package com.teamfair.modulequest.application.command

data class CreateQuestCommand(
    val imageId: Long? = null,
    val writerName: String? = null,
    val mainImageId: Long? = null,
    val popularYn: Boolean = false,
    val type: String,
    val repeatFrequency: String? = null,
    val sortOrder: Int = 0
) 