package com.illsang.quest.domain.model.history

import com.illsang.common.domain.model.BaseModel
import com.illsang.quest.domain.entity.history.UserQuestHistoryEntity
import com.illsang.quest.enums.QuestStatus
import java.time.LocalDateTime

data class ChallengeModel(
    val id: Long,
    val userId: String,
    val questId: Long,
    val status: QuestStatus,
    override val createdBy: String? = null,
    override val createdAt: LocalDateTime? = null,
    override val updatedBy: String? = null,
    override val updatedAt: LocalDateTime? = null
) : BaseModel(createdBy, createdAt, updatedBy, updatedAt) {

    companion object {
        fun from(history: UserQuestHistoryEntity): ChallengeModel {
            return ChallengeModel(
                id = history.id!!,
                userId = history.userId,
                questId = history.quest.id!!,
                status = history.status,
                createdBy = history.createdBy,
                createdAt = history.createdAt,
                updatedBy = history.updatedBy,
                updatedAt = history.updatedAt,
            )
        }
    }

}
