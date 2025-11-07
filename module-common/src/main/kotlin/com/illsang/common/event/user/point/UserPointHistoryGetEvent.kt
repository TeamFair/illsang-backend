package com.illsang.common.event.user.point

import com.illsang.common.enums.PointType
import com.illsang.common.event.user.info.UserInfoGetEvent.UserTitleInfo

data class UserPointHistoryGetEvent(
    val userQuestHistoryId: Long?,

) {
    lateinit var response: List<UserPointHistory>

    class UserPointHistory (
        val userCommercialAreaCode: String?,
        val commercialAreaCode: String,
        val point: Int,
        val pointType: PointType,
    )
}