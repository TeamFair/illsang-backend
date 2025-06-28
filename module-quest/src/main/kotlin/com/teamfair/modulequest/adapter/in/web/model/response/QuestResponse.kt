package com.teamfair.modulequest.adapter.`in`.web.model.response

import com.teamfair.modulequest.domain.model.QuestModel
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
        fun from(questModel: QuestModel): QuestResponse {
            return QuestResponse(
                id = questModel.id,
                imageId = questModel.imageId,
                writerName = questModel.writerName,
                mainImageId = questModel.mainImageId,
                popularYn = questModel.popularYn,
                type = questModel.type,
                repeatFrequency = questModel.repeatFrequency,
                sortOrder = questModel.sortOrder,
                createdBy = questModel.createdBy,
                createdAt = questModel.createdAt,
                updatedBy = questModel.updatedBy,
                updatedAt = questModel.updatedAt
            )
        }
    }
} 