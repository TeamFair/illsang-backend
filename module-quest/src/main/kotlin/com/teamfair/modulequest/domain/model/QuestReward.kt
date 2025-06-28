package com.teamfair.modulequest.domain.model

import com.illsang.common.model.BaseModel
import com.teamfair.modulequest.domain.model.enums.RewardType
import java.time.LocalDateTime

data class QuestReward(
    val id: Long? = null,
    var type: RewardType,
    var amount: Int,
    val questId: Long,
    override val createdBy: String? = null,
    override val createdAt: LocalDateTime? = null,
    override val updatedBy: String? = null,
    override val updatedAt: LocalDateTime? = null
) : BaseModel(createdBy, createdAt, updatedBy, updatedAt) 