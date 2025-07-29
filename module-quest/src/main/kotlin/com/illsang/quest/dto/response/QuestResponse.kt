package com.illsang.quest.dto.response

import com.illsang.quest.domain.model.QuestModel
import com.illsang.quest.enums.QuestRepeatFrequency
import com.illsang.quest.enums.QuestType
import java.time.LocalDateTime

data class QuestResponse(
    val id: Long?,
    val imageId: String?,
    val writerName: String?,
    val mainImageId: String?,
    val popularYn: Boolean,
    val type: QuestType,
    val repeatFrequency: QuestRepeatFrequency?,
    val sortOrder: Int,
    val createdBy: String?,
    val createdAt: LocalDateTime?,
    val updatedBy: String?,
    val updatedAt: LocalDateTime?,
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
                updatedAt = questModel.updatedAt,
            )
        }
    }
}
