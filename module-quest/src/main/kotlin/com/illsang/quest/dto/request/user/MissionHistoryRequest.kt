package com.illsang.quest.dto.request.user

import com.illsang.quest.enums.EmojiType
import com.illsang.quest.enums.MissionType

data class MissionHistoryEmojiCreateRequest (
    val emojiType: EmojiType,
)

data class MissionHistoryRequest (
    var userId: String?,
    val missionType: MissionType? = null,
    val orderRewardDesc: Boolean? = null,
    val orderCreatedAtDesc: Boolean? = true,
)

data class MissionHistoryReportRequest(
    val reason: String? = null,
)