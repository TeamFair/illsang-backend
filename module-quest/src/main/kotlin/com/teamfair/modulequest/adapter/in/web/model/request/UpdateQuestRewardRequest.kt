package com.teamfair.modulequest.adapter.`in`.web.model.request

import com.teamfair.modulequest.domain.model.enums.RewardType

data class UpdateQuestRewardRequest(
    val type: RewardType? = null,
    val amount: Int? = null
) 