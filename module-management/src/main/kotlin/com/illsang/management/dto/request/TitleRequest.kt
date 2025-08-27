package com.illsang.management.dto.request

import com.illsang.common.enums.TitleGrade
import com.illsang.common.enums.TitleType
import com.illsang.management.domain.entity.TitleEntity

data class TitleRequest(
    val id : String,
    val name: String,
    val type: TitleType,
    val grade: TitleGrade,
    val condition: String,
    val useYn : Boolean = false
){
    fun toEntity(): TitleEntity {
        return TitleEntity(
            id = id,
            name = name,
            type = type,
            grade = grade,
            condition = condition,
            useYn = useYn

        )
    }
}