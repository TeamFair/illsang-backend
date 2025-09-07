package com.illsang.common.event.user.info

import com.illsang.common.enums.TitleGrade
import com.illsang.common.enums.TitleType

data class UserInfoBatchGetEvent (
    val userIds: List<String>,
) {
    lateinit var response: List<UserInfo>

    class UserInfo (
        val userId: String,
        val nickname: String,
        val profileImageId: String?,
        val title: UserTitleInfo?,
        val commercialAreaCode: String?,
    )

    class UserTitleInfo (
        val name: String,
        val grade: TitleGrade,
        val type: TitleType,
    )
}

data class UserInfoGetEvent (
    val userId: String,
) {
    lateinit var response: UserInfo

    class UserInfo (
        val userId: String,
        val nickname: String,
        val profileImageId: String?,
        val title: UserTitleInfo?,
        val commercialAreaCode: String?,
    )

    class UserTitleInfo (
        val name: String,
        val grade: TitleGrade,
        val type: TitleType,
    )
}


