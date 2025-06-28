package com.teamfair.modulequest.adapter.`in`.web.model.response

import com.teamfair.modulequest.domain.model.Quest
import java.time.LocalDateTime

data class QuestResponse(
    val id: Long?,
    val imageId: Long?,
    val writerName: String?,
    val mainImageId: Long?,
    val popularYn: Boolean,
    val type: String,
    val repeatFrequency: String?,
    val sortOrder: Int,
    val createdBy: String?,
    val createdAt: LocalDateTime?,
    val updatedBy: String?,
    val updatedAt: LocalDateTime?
) {
    companion object {
        fun from(quest: Quest): QuestResponse {
            return QuestResponse(
                id = quest.id,
                imageId = quest.imageId,
                writerName = quest.writerName,
                mainImageId = quest.mainImageId,
                popularYn = quest.popularYn,
                type = quest.type,
                repeatFrequency = quest.repeatFrequency,
                sortOrder = quest.sortOrder,
                createdBy = quest.createdBy,
                createdAt = quest.createdAt,
                updatedBy = quest.updatedBy,
                updatedAt = quest.updatedAt
            )
        }
    }
} 