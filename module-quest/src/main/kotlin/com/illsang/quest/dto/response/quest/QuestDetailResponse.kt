package com.illsang.quest.dto.response.quest

import com.illsang.quest.domain.model.quset.QuestDetailModel
import com.illsang.quest.enums.QuestRepeatFrequency
import com.illsang.quest.enums.QuestType
import java.time.LocalDateTime

data class QuestDetailResponse(
    val id: Long?,
    val title: String,
    val imageId: String?,
    val writerName: String?,
    val mainImageId: String?,
    val popularYn: Boolean,
    val type: QuestType,
    val repeatFrequency: QuestRepeatFrequency?,
    val sortOrder: Int,
    val expireDate: LocalDateTime?,
    val bannerId: Long?,
    var commercialAreaCode: String,
    val useYn: Boolean,
    val rewards: List<QuestRewardResponse>,
    val missions: List<MissionDetailResponse>,
    val createdBy: String?,
    val createdAt: LocalDateTime?,
    val updatedBy: String?,
    val updatedAt: LocalDateTime?,
) {
    companion object {
        fun from(questModel: QuestDetailModel): QuestDetailResponse {
            return QuestDetailResponse(
                id = questModel.id,
                title = questModel.title,
                imageId = questModel.imageId,
                writerName = questModel.writerName,
                mainImageId = questModel.mainImageId,
                popularYn = questModel.popularYn,
                type = questModel.type,
                repeatFrequency = questModel.repeatFrequency,
                sortOrder = questModel.sortOrder,
                expireDate = questModel.expireDate,
                bannerId = questModel.bannerId,
                commercialAreaCode = questModel.commercialAreaCode,
                useYn = questModel.useYn,
                rewards = questModel.rewards.map { QuestRewardResponse.from(it) },
                missions = questModel.missions.map { MissionDetailResponse.from(it) },
                createdBy = questModel.createdBy,
                createdAt = questModel.createdAt,
                updatedBy = questModel.updatedBy,
                updatedAt = questModel.updatedAt,
            )
        }
    }
}
