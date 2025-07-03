package com.illsang.modulequest.application.command

import com.illsang.modulequest.domain.model.enums.RewardType

data class UpdateQuestRewardCommand(
    val id: Long,
    val type: RewardType? = null,
    val amount: Int? = null
)
