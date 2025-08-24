package com.illsang.management.dto.response

import com.illsang.common.enums.TitleGrade
import com.illsang.common.enums.TitleType
import com.illsang.management.domain.model.TitleModel

data class TitleResponse(
    val id : String,
    val name: String,
    val type: TitleType,
    val grade: TitleGrade,
    val condition: String? = null,
    val useYn : Boolean = false
) {
    companion object {
        fun from(title: TitleModel) = TitleResponse(
            id = title.id,
            name = title.name,
            type = title.type,
            grade = title.grade,
            condition = title.condition,
            useYn = title.useYn
        )
    }
}