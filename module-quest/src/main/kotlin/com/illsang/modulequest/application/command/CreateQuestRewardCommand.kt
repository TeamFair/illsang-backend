package com.illsang.modulequest.application.command

import com.illsang.modulequest.domain.model.enums.RewardType

data class CreateQuestRewardCommand(
    val type: RewardType,
    val amount: Int,
    val questId: Long
)
