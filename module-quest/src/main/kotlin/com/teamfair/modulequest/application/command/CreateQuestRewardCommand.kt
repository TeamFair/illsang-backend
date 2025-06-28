package com.teamfair.modulequest.application.command

import com.teamfair.modulequest.domain.model.enums.RewardType

data class CreateQuestRewardCommand(
    val questId: Long,
    val type: RewardType,
    val amount: Int
) 