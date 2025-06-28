package com.teamfair.modulequest.domain.model

import com.illsang.common.model.BaseModel
import com.teamfair.modulequest.domain.model.enums.QuestStatus
import java.time.LocalDateTime

data class UserQuestHistory(
    val id: Long? = null,
    val userId: Long,
    var status: QuestStatus = QuestStatus.PROGRESSING,
    var liked: Boolean = false,
    var disliked: Boolean = false,
    var viewCount: Int = 0,
    val questId: Long,
    override val createdBy: String? = null,
    override val createdAt: LocalDateTime? = null,
    override val updatedBy: String? = null,
    override val updatedAt: LocalDateTime? = null
) : BaseModel(createdBy, createdAt, updatedBy, updatedAt) 