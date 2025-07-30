package com.illsang.quest.dto.request

import com.illsang.quest.domain.entity.QuestEntity
import com.illsang.quest.enums.QuestRepeatFrequency
import com.illsang.quest.enums.QuestType
import java.time.LocalDateTime

data class QuestCreateRequest(
    val title: String,
    val imageId: String? = null,
    val writerName: String,
    val mainImageId: String? = null,
    val popularYn: Boolean = false,
    val type: QuestType,
    val repeatFrequency: QuestRepeatFrequency? = null,
    val sortOrder: Int = 0,
    val expireDate: LocalDateTime? = null,
    val bannerId: Long? = null,
    val commercialAreaCode: String,
    val rewards: List<QuestRewardCreateRequest>,
) {

    fun toEntity(): QuestEntity {
        return QuestEntity(
            title = this.title,
            imageId = this.imageId,
            writerName = this.writerName,
            mainImageId = this.mainImageId,
            popularYn = this.popularYn,
            type = this.type,
            repeatFrequency = this.repeatFrequency,
            sortOrder = this.sortOrder,
            expireDate = this.expireDate,
            bannerId = this.bannerId,
            commercialAreaCode = this.commercialAreaCode,
        )
    }

}

data class QuestUpdateRequest(
    val title: String,
    val imageId: String? = null,
    val writerName: String,
    val mainImageId: String? = null,
    val popularYn: Boolean = false,
    val type: QuestType,
    val repeatFrequency: QuestRepeatFrequency? = null,
    val sortOrder: Int = 0,
    val expireDate: LocalDateTime? = null,
    val bannerId: Long? = null,
    val commercialAreaCode: String,
    val useYn: Boolean,
)
