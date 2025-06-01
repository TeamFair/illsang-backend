package com.teamfair.modulequest.domain.model

import com.teamfair.modulequest.domain.model.enums.RewardType

data class QuestReward(
    val id: Long? = null,
    var type: RewardType,
    var amount: Int,
    val createdBy: String? = null,
    val createdAt: String? = null,
    val updatedBy: String? = null,
    val updatedAt: String? = null
) 