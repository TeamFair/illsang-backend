package com.illsang.quest.dto.request.quest

import com.illsang.quest.enums.QuestRepeatFrequency
import com.illsang.quest.enums.QuestType

data class QuestUserRequest(
    val userId: String,
    val popularYn: Boolean? = null,
    val commercialAreaCode: String? = null,
    val questType: QuestType? = null,
    val repeatFrequency: QuestRepeatFrequency? = null,
    val orderRewardDesc: Boolean? = null,
    val completedYn: Boolean? = null,
    val favoriteYn: Boolean? = null,
    val orderExpiredDesc: Boolean? = null,
    val bannerId: Long? = null,
)

data class QuestUserTypeRequest(
    val questType: QuestType? = null,
    val repeatFrequency: QuestRepeatFrequency? = null,
    val orderExpiredDesc: Boolean? = null,
    val orderRewardDesc: Boolean? = null,
    val favoriteYn: Boolean? = null,
    val completedYn: Boolean? = null,
)

data class QuestUserBannerRequest(
    val completedYn: Boolean? = null,
    val orderExpiredDesc: Boolean? = null,
    val orderRewardDesc: Boolean? = null,
)

