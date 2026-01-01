package com.illsang.quest.dto.request.quest

import com.fasterxml.jackson.annotation.JsonIgnore
import com.illsang.quest.domain.entity.quest.QuestEntity
import com.illsang.quest.enums.MissionType
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
    val storeId: Long? = null,
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
            storeId = storeId,
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
    val storeId: Long? = null,
)

data class QuestGetListRequest(
    val type: QuestType? = null,
    val missionType: MissionType? = null,
    val repeatFrequency: QuestRepeatFrequency? = null,
    val commercialAreaCode: String? = null,
    val metroAreaCode: String? = null,
) {
    @JsonIgnore
    var commercialAreaCodes: MutableList<String>? = null

    init {
        commercialAreaCode?.let {
            commercialAreaCodes = mutableListOf(it)
        }
    }

    fun appendCommercialAreaCodes(commercialAreaCodes: List<String>) {
        val currentCodes = this.commercialAreaCodes
        if (currentCodes != null) {
            this.commercialAreaCodes = commercialAreaCodes.intersect(currentCodes.toSet()).toMutableList()
        } else {
            this.commercialAreaCodes = commercialAreaCodes.toMutableList()
        }
    }
}
