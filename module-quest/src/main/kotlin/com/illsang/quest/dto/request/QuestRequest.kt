package com.illsang.quest.dto.request

import com.illsang.quest.domain.entity.QuestEntity
import com.illsang.quest.enums.QuestRepeatFrequency
import com.illsang.quest.enums.QuestType

data class QuestCreateRequest(
    val imageId: String? = null,
    val writerName: String,
    val mainImageId: String? = null,
    val popularYn: Boolean = false,
    val type: QuestType,
    val repeatFrequency: QuestRepeatFrequency? = null,
    val sortOrder: Int = 0,
) {

    fun toEntity(): QuestEntity {
        return QuestEntity(
            imageId = this.imageId,
            writerName = this.writerName,
            mainImageId = this.mainImageId,
            popularYn = this.popularYn,
            type = this.type,
            repeatFrequency = this.repeatFrequency,
            sortOrder = this.sortOrder,
        )
    }

}

data class QuestUpdateRequest(
    val imageId: String? = null,
    val writerName: String,
    val mainImageId: String? = null,
    val popularYn: Boolean = false,
    val type: QuestType,
    val repeatFrequency: QuestRepeatFrequency? = null,
    val sortOrder: Int = 0,
)
