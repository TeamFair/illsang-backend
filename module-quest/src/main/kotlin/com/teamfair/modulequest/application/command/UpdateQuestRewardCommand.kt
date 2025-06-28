package com.teamfair.modulequest.application.command

import com.teamfair.modulequest.domain.model.enums.RewardType

data class UpdateQuestRewardCommand(
    val id: Long,
    val type: RewardType? = null,
    val amount: Int? = null
) 