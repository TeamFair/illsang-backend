package com.illsang.user.listener

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
    fun findAll(event: UserInfoGetEvent) {
        val users = this.userService.findAll(event.userIds)

        event.response = users.map {
            UserInfoGetEvent.UserInfo(
                userId = it.id!!,
                nickname = it.nickname,
                profileImageId = it.profileImageId,
                title = it.title?.let { title ->
                    UserInfoGetEvent.UserTitleInfo(
                        name = title.titleName,
                        grade = title.titleGrade,
                        type = title.titleType,
                    )
                }
            )
        }
    }

    @EventListener
    fun existImageId(event : UserProfileImageExistOrThrowEvent){
        userService.existUserProfileImageId(event.imageId)
    }

}
