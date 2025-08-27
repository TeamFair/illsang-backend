package com.illsang.management.domain.model

import com.illsang.common.domain.model.BaseModel
import com.illsang.common.enums.TitleGrade
import com.illsang.common.enums.TitleType
import com.illsang.management.domain.entity.TitleEntity
import java.time.LocalDateTime

data class TitleModel(
    var id: String,
    val name: String,
    val type: TitleType,
    val grade: TitleGrade,
    val condition: String? = null,
    val useYn: Boolean = false,
    override val createdBy: String? = null,
    override val createdAt: LocalDateTime? = null,
    override val updatedBy: String? = null,
    override val updatedAt: LocalDateTime? = null
) : BaseModel(createdBy, createdAt, updatedBy, updatedAt) {

    companion object {
        fun from(entity: TitleEntity) = TitleModel(
            id = entity.id,
            name = entity.name,
            type = entity.type,
            grade = entity.grade,
            condition = entity.condition,
            useYn = entity.useYn,
            createdBy = entity.createdBy,
            createdAt = entity.createdAt,
            updatedBy = entity.updatedBy,
            updatedAt = entity.updatedAt
        )
    }

}