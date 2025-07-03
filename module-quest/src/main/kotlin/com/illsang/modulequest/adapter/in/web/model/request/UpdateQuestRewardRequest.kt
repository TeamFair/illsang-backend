package com.illsang.modulequest.adapter.`in`.web.model.request

import com.illsang.modulequest.domain.model.enums.RewardType

data class UpdateQuestRewardRequest(
    val type: RewardType? = null,
    val amount: Int? = null
)
