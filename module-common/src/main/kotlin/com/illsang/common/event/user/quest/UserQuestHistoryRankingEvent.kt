package com.illsang.common.event.user.quest

import com.illsang.common.enums.CouponType
import com.illsang.common.enums.RankType

class UserQuestHistoryRankingEvent(
    val type: CouponType? = null,
    val storeId: Long? = null,
    val rankType: RankType? = null,
) {
    lateinit var response: List<String>
}