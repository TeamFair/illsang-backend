package com.illsang.user.dto.request

import com.illsang.common.enums.TitleGrade
import com.illsang.common.enums.TitleType
import com.illsang.user.domain.entity.UserEntity
import com.illsang.user.domain.entity.UserTitleEntity

data class UserTitleRequest(
    val titleId : String,
    val titleType : TitleType,
    val titleName : String,
    val titleGrade : TitleGrade,
    val readYn : Boolean,
    val userId : String
) {
    fun toEntity(user : UserEntity): UserTitleEntity {
        return UserTitleEntity(
            titleId = titleId,
            titleType = titleType,
            titleName = titleName,
            titleGrade = titleGrade,
            readYn = readYn,
            user = user

        )
    }

}