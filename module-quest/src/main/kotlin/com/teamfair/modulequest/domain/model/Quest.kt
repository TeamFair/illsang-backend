package com.teamfair.modulequest.domain.model

data class Quest(
    val id: Long? = null,
    var imageId: Long? = null,
    var writerName: String? = null,
    var mainImageId: Long? = null,
    var popularYn: Boolean = false,
    var type: String,
    var repeatFrequency: String? = null,
    var sortOrder: Int = 0,
    val missions: MutableList<Mission> = mutableListOf(),
    val rewards: MutableList<QuestReward> = mutableListOf(),
    val userHistories: MutableList<UserQuestHistory> = mutableListOf(),
    val createdBy: String? = null,
    val createdAt: String? = null,
    val updatedBy: String? = null,
    val updatedAt: String? = null
) 