package com.illsang.user.listener

import com.illsang.common.event.user.info.UserInfoBatchGetEvent
import com.illsang.common.event.user.info.UserInfoGetEvent
import com.illsang.common.event.user.info.UserProfileImageExistOrThrowEvent
import com.illsang.user.service.UserService
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class UserInfoEventListener(
    private val userService: UserService,
) {

    @EventListener
    fun findAll(event: UserInfoBatchGetEvent) {
        val users = this.userService.findAll(event.userIds)

        event.response = users.map {
            UserInfoBatchGetEvent.UserInfo(
                userId = it.id!!,
                nickname = it.nickname,
                profileImageId = it.profileImageId,
                title = it.title?.let { title ->
                    UserInfoBatchGetEvent.UserTitleInfo(
                        name = title.titleName,
                        grade = title.titleGrade,
                        type = title.titleType,
                    )
                },
                commercialAreaCode = it.commercialAreaCode,
            )
        }
    }

    @EventListener
    fun find(event: UserInfoGetEvent) {
        val user = this.userService.getUser(event.userId)

        event.response =
            UserInfoGetEvent.UserInfo(
                userId = user.id!!,
                nickname = user.nickname,
                profileImageId = user.profileImageId,
                title = user.title?.let { title ->
                    UserInfoGetEvent.UserTitleInfo(
                        name = title.titleName,
                        grade = title.titleGrade,
                        type = title.titleType,
                    )
                },
                commercialAreaCode = user.commercialAreaCode,
            )
    }

    @EventListener
    fun existImageId(event: UserProfileImageExistOrThrowEvent) {
        userService.existUserProfileImageId(event.imageId)
    }

}
