package com.illsang.common.event.user.title

import com.illsang.common.enums.TitleGrade
import com.illsang.common.enums.TitleType

data class GetTitleInfoEvent(
    var titleId: String,
){
    lateinit var response: TitleInfo

    class TitleInfo (
        val titleId: String,
        val titleName: String,
        val titleGrade: TitleGrade,
        val titleType: TitleType,
    )
}