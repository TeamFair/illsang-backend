package com.illsang.user.domain.model

import com.illsang.common.domain.model.BaseModel
import com.illsang.common.enums.TitleGrade
import com.illsang.common.enums.TitleType
import com.illsang.user.domain.entity.UserEntity
import com.illsang.user.domain.entity.UserTitleEntity
import java.time.LocalDateTime

data class UserTitleForPointModel(
    val id: Long?,
    val userId: String,
    var titleId: String,
    var titleName: String,
    var titleGrade: TitleGrade,
    var titleType: TitleType,
    var readYn: Boolean,
    var point: Long?,
    val user: UserEntity,
    override val createdBy: String? = null,
    override val createdAt: LocalDateTime? = null,
    override val updatedBy: String? = null,
    override val updatedAt: LocalDateTime? = null
) : BaseModel(createdBy, createdAt, updatedBy, updatedAt) {
    companion object {
        fun from(currentTitle: UserTitleEntity, user: UserEntity, point: Long?): UserTitleForPointModel {
            return UserTitleForPointModel(
                id = currentTitle.id,
                userId = currentTitle.user.id!!,
                titleId = currentTitle.titleId,
                titleName = currentTitle.titleName,
                titleGrade = currentTitle.titleGrade,
                titleType = currentTitle.titleType,
                readYn = currentTitle.readYn,
                createdBy = currentTitle.createdBy,
                createdAt = currentTitle.createdAt,
                updatedBy = currentTitle.updatedBy,
                updatedAt = currentTitle.updatedAt,
                user = user,
                point = point,
            )
        }
    }
}