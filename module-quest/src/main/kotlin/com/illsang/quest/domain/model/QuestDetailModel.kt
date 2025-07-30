package com.illsang.quest.domain.model

import com.illsang.common.domain.model.BaseModel
import com.illsang.quest.domain.entity.QuestEntity
import com.illsang.quest.enums.QuestRepeatFrequency
import com.illsang.quest.enums.QuestType
import java.time.LocalDateTime

data class QuestDetailModel(
    val id: Long? = null,
    var title: String,
    var imageId: String? = null,
    var writerName: String? = null,
    var mainImageId: String? = null,
    var popularYn: Boolean = false,
    var type: QuestType,
    var repeatFrequency: QuestRepeatFrequency? = null,
    var sortOrder: Int = 0,
    var expireDate: LocalDateTime? = null,
    var bannerId: Long? = null,
    var commercialAreaCode: String,
    var useYn: Boolean,
    var rewards: List<QuestRewardModel>,
    var missions: List<MissionDetailModel>,
    override val createdBy: String? = null,
    override val createdAt: LocalDateTime? = null,
    override val updatedBy: String? = null,
    override val updatedAt: LocalDateTime? = null
) : BaseModel(createdBy, createdAt, updatedBy, updatedAt) {

    companion object {
        fun from(quest: QuestEntity): QuestDetailModel {
            return QuestDetailModel(
                id = quest.id,
                title = quest.title,
                imageId = quest.imageId,
                writerName = quest.writerName,
                mainImageId = quest.mainImageId,
                popularYn = quest.popularYn,
                type = quest.type,
                repeatFrequency = quest.repeatFrequency,
                sortOrder = quest.sortOrder,
                expireDate = quest.expireDate,
                bannerId = quest.bannerId,
                commercialAreaCode = quest.commercialAreaCode,
                useYn = quest.useYn,
                rewards = quest.rewards.map { QuestRewardModel.from(it) },
                missions = quest.missions.map { MissionDetailModel.from(it) },
                createdBy = quest.createdBy,
                createdAt = quest.createdAt,
                updatedBy = quest.updatedBy,
                updatedAt = quest.updatedAt,
            )
        }
    }

}
