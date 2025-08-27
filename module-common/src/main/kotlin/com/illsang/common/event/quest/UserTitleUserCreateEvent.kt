package com.illsang.common.event.quest

import com.illsang.common.enums.TitleGrade
import com.illsang.common.enums.TitleType

data class UserTitleUserCreateEvent (
    val userId: String,
    val titleId: String,
    val titleName: String,
    val titleGrade: TitleGrade,
    val titleType: TitleType,
)