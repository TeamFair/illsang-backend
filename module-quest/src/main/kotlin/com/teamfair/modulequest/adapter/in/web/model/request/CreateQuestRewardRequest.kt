package com.teamfair.modulequest.adapter.`in`.web.model.request

import com.teamfair.modulequest.domain.model.enums.RewardType

data class CreateQuestRewardRequest(
    val type: RewardType,
    val amount: Int,
    val questId: Long
) 