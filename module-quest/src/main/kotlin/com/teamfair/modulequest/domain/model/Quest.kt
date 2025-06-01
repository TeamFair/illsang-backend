package com.teamfair.modulequest.domain.model

data class Quest(
    val id: Long? = null,
    var title: String,
    var description: String? = null,
    var imageUrl: String? = null,
    var isRepeatable: Boolean = false,
    var isPublished: Boolean = false,
    val missions: MutableList<Mission> = mutableListOf(),
    val rewards: MutableList<QuestReward> = mutableListOf(),
    val userHistories: MutableList<UserQuestHistory> = mutableListOf(),
    val createdBy: String? = null,
    val createdAt: String? = null,
    val updatedBy: String? = null,
    val updatedAt: String? = null
) 